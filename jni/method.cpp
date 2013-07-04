#include <opencv2/opencv.hpp>
#include <math.h>	//To round, cos, sin, etc
#include <ctime>	//To random
#include <iomanip>	//To setprecision of double output
#include <android/log.h>
using namespace cv;

#define LOG_TAG "TestUSG/jni_part"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

typedef struct {
	int x, y;
	float fit;
} Triple;

typedef struct {
	double a, b, sudut, x, y, fit;
} LocalBest;

typedef struct {
	double a, b, sudut;
} Velocity;

typedef struct {
	double a, b, sudut;
	Triple fitness;
	LocalBest lbest;
	Velocity prevV;
} Particle;

typedef struct {
	int xc, yc, theta, a, b;
} EllipseParams;

typedef struct {
	double x, y;
	bool isActive;
} Titik;

const double PI = 3.141592;
char aa[80];
int sampleNumber = 5;
int filteredCount;
int ITERATION, INTERVAL;
float DELTA_RECT;
uchar candidateThreshold = 128;
float minA, maxA, segA, minB, maxB, segB, minTetha, maxTetha, segTetha;
bool ECCENTRIC_MODE = true;
int N_PARTICLE = 75;
int N_ITERATION = 10;
int N_TRY_MID_POINT = 500; //awalnya 100 aja udah keren
float C1 = 1.1f;
float C2 = 0.2f;
float MIN_ECC = 0.7;
float MAX_ECC = 0.9;
int SAMPLING_POINT;
int bestIndex = 0;
Particle population[75];
Particle globalBest;

EllipseParams highest, highestBefore, Tolerance;
int accumulatorSize = 0;
std::vector<int> xAccumulator;
std::vector<int> yAccumulator;
std::vector<int> aAccumulator;
std::vector<int> bAccumulator;
std::vector<int> tAccumulator;

Titik chosenPoints[5];
Titik centerPoints[2];
int indexes[5];
Triple htResult;

float BOUND;
int iterationRun = 1;
int inactivePoints = 0;

double convertRadToDegree(double rad) {
	return rad * 180 / PI;
}

double acak() {
	return rand() / double(RAND_MAX); //range angka adalah 0-1
}

std::vector<Titik> getCandidatePoints(Mat& image) {
	int size = image.cols * image.rows;
	std::vector<Titik> filteredPoints;
	filteredCount = 0;

	for (int i = 0; i < image.rows; i++) {
		for (int j = 0; j < image.cols; j++) {
			Point3_<uchar>* p1 = image.ptr<Point3_<uchar> >(i, j);
			float a = p1->x;
			float b = p1->y;
			float c = p1->z;
			if ((a + b + c) / 3 > 128) {
				Titik h;
				h.isActive = true;
				h.x = j;
				h.y = i;
				filteredPoints.push_back(h);
				filteredCount++;
			}
		}
	}
	return filteredPoints;
}

void mask2(std::vector<Titik>& points, Mat& rectMask) {
	std::cout << "Entered mask 2" << std::endl;
	Titik temp;
	int counter = 0;
	int pointCounter = points.size();

	for (int i = 0; i < pointCounter; i++) {
		Titik temp = points[i];
		if (rectMask.at<uchar>(temp.y, temp.x) == 0 && temp.isActive) {
			temp = points[i];
			temp.isActive = false;
			counter++;
		}
	}
	inactivePoints = counter;
	std::cout << "eliminated points : " << counter << std::endl;
}

void eliminatePoint(Mat& rect, std::vector<Titik>& points, float xc, float yc,
		float a, float b, float angle) {
	std::cout << "Entered eliminate Point" << std::endl;
	rect *= 0;
	a += BOUND;
	b += BOUND;
	RotatedRect aaa = RotatedRect(Point2f(xc, yc), Point2f(a, b), angle);
	CvPoint2D32f pointsf[4];
	cvBoxPoints(aaa, pointsf);
	Point pointsi[1][4];
	for (int i = 0; i < 4; i++) {
		pointsi[0][i] = cvPointFrom32f(pointsf[i]);
	}
	const Point* countours[1] = { pointsi[0] };
	const Point* ppt[1] = { pointsi[0] };
	int npt[] = { 4 };
	fillPoly(rect, ppt, npt, 1, cvScalar(255, 255, 255), 8);
	mask2(points, rect);
}

bool contain(int* collection, int size, int element) {
	for (int i = 0; i < size; i++) {
		if (collection[i] == element) {
			return true;
		}
	}
	return false;
}

void getRandomPoints(std::vector<Titik>& points, int number) {
	int indexed = 0;
	int randomInt;
	Titik temp;
	while (indexed < number) {
		do {
			randomInt = rand() % filteredCount;
			temp = points[randomInt];
		} while (!temp.isActive || contain(indexes, 5, randomInt));
		indexes[indexed] = randomInt;
		chosenPoints[indexed] = temp;
		indexed++;
	}
}

void debugFloatArray(Mat a) {
	//std::cout << a << std::endl << std::endl;
}

EllipseParams solveEllipseMatrixEquation() {
	float a[5][5];
	float b[5][1];
	Titik temp;
	for (int i = 0; i < sampleNumber; i++) {
		temp = chosenPoints[i];
		a[i][0] = pow((float) temp.x, 2) - pow((float) temp.y, 2);
		a[i][1] = 2 * temp.x * temp.y;
		a[i][2] = temp.x;
		a[i][3] = temp.y;
		a[i][4] = 1;

		b[i][0] = pow((float) temp.x, 2) + pow((float) temp.y, 2);
	}
	Mat a1, b1, c;
//	a1 = Mat(sampleNumber, sampleNumber, CV_32FC1, a);
	//b1 = Mat(sampleNumber, 1, CV_32FC1, b);

	//debugFloatArray(a1);
//	debugFloatArray(b1);
	c = (a1.t() * a1).inv() * (a1.t() * b1);
//	debugFloatArray(c);
	Mat d = a1 * c;
//	debugFloatArray(d);

	float U = c.at<float>(0, 0), V = c.at<float>(1, 0), R = c.at<float>(2, 0),
			S = c.at<float>(3, 0), T = c.at<float>(4, 0);

	float xc = (S * V + R + R * U) / (2 * (1 - U * U - V * V)), yc = (R * V + S
			- S * U) / (2 * (1 - U * U - V * V)), theta = convertRadToDegree(
			(atan(V / U)) / 2), alpha = sqrt(
			(2 * T + xc * R + yc * S) / (2 * (1 - sqrt(U * U + V * V)))), beta =
			sqrt((2 * T + xc * R + yc * S) / (2 * (1 + sqrt(U * U + V * V))));

	/*std::cout << std::endl << "xc-yc-theta-a-b:" << xc << "-" << yc << "-"
	 << theta << "-" << alpha << "-" << beta << std::endl;
	 std::cout << "UVRST:" << U << "_" << V << "_" << R << "_"<< S << "_" << T <<std::endl;
	 */
	EllipseParams p;
	p.xc = xc;
	p.yc = yc;
	p.a = alpha;
	p.b = beta;
	p.theta = theta;
	return p;

}

void mainIRHT(Mat& image, float* ellRes) {
	srand(time(NULL));
	iterationRun = 1;
	BOUND = 50; //weo

	Mat rect(image.rows, image.cols, CV_8UC1);
	minA = 0.23 * image.cols;
	maxA = 0.54 * image.cols;
	minB = 0.16 * image.cols;
	maxB = 0.45 * image.cols;
	maxTetha = 180;

	std::vector<Titik> filteredPoints = getCandidatePoints(image);
	int allPoint = filteredPoints.size();
	SAMPLING_POINT = filteredPoints.size();

	//Inisialisasi 5 akumulator 1D

	accumulatorSize = (image.cols > image.rows) ? image.cols : image.rows;
	xAccumulator = std::vector<int>(accumulatorSize);
	yAccumulator = std::vector<int>(accumulatorSize);
	aAccumulator = std::vector<int>(accumulatorSize);
	bAccumulator = std::vector<int>(accumulatorSize);
	tAccumulator = std::vector<int>(accumulatorSize);

	for (int i = 0; i < accumulatorSize; i++) {
		xAccumulator[i] = 0;
		yAccumulator[i] = 0;
		aAccumulator[i] = 0;
		bAccumulator[i] = 0;
		tAccumulator[i] = 0;
	}
	highest.a = 0;
	highest.b = 0;
	highest.xc = 0;
	highest.yc = 0;
	highest.theta = 0;

	Tolerance.xc = 4;
	Tolerance.yc = 4;
	Tolerance.theta = 4;
	Tolerance.a = 4;
	Tolerance.b = 4;
	bool notConvergen = true;
	int irhtIteration = 0;

	while (notConvergen && (iterationRun == 1)) {
		irhtIteration++;
		//std::cout << "Iteration : " << i << std::endl;
		highestBefore = highest;
		for (int i = 0; i < SAMPLING_POINT; i++) {
			bool isValidParams = false;
			EllipseParams params;
			while (!isValidParams) {
				getRandomPoints(filteredPoints, sampleNumber);
				params = solveEllipseMatrixEquation();

				if (params.a > 0 && params.b > 0 && params.xc > 0
						&& params.yc > 0) {
					isValidParams = true;
				}
			}
			float ecc = ((float) params.b) / params.a;
			if (ecc <= MAX_ECC && ecc >= MIN_ECC && params.a <= maxA
					&& params.a >= minA && params.b <= maxB && params.b >= minB
					&& params.xc > -1 && params.yc > -1
					&& params.xc < accumulatorSize
					&& params.yc < accumulatorSize) {

				if (params.theta < 0) {
					params.theta += 360;
				}

				aAccumulator[params.a] += 1;
				bAccumulator[params.b] += 1;
				xAccumulator[params.xc] += 1;
				yAccumulator[params.yc] += 1;
				tAccumulator[params.theta] += 1;

				if (aAccumulator[highest.a] < aAccumulator[params.a]) {
					highest.a = params.a;
				}
				if (bAccumulator[highest.b] < bAccumulator[params.b]) {
					highest.b = params.b;
				}
				if (xAccumulator[highest.xc] < xAccumulator[params.xc]) {
					highest.xc = params.xc;
				}
				if (yAccumulator[highest.yc] < yAccumulator[params.yc]) {
					highest.yc = params.yc;
				}
				if (tAccumulator[highest.theta] < tAccumulator[params.b]) {
					highest.theta = params.theta;
				}
			}
		}
		//CEKING CONVERGENCY
		float lowA = highest.a - highestBefore.a;
		float lowB = highest.b - highestBefore.b;
		float lowT = highest.theta - highestBefore.theta;
		float lowX = highest.xc - highestBefore.xc;
		float lowY = highest.yc - highestBefore.yc;

		if ((abs(lowA) > Tolerance.a) || (abs(lowB) > Tolerance.b)
				|| (abs(lowT) > Tolerance.theta) || (abs(lowX) > Tolerance.xc)
				|| (abs(lowY) > Tolerance.yc)) {
			eliminatePoint(rect, filteredPoints, highest.xc, highest.yc,
					highest.a * 2, highest.b * 2, highest.theta);
			if ((float) inactivePoints > (float) (0.65 * allPoint)) {
				iterationRun = 0;
				break;
			}
		} else {
			notConvergen = false;
		}

		if (irhtIteration > 10) {
			break;
		}
	}
//	sprintf(aa, "Iteration %d", irhtIteration);
//	LOGD(aa);

	ellRes[0] = (float) highest.xc;
	ellRes[1] = (float) highest.yc;
	ellRes[2] = (float) highest.a;
	ellRes[3] = (float) highest.b;
	ellRes[4] = (float) highest.theta;
	ellRes[5] = -1;
	ellipse(image, Point(highest.xc, highest.yc), Size(highest.a, highest.b),
			highest.theta, 0, 360, Scalar(255, 0, 0), 1, 8, 0);

	filteredPoints.clear();
	xAccumulator.clear();
	yAccumulator.clear();
	aAccumulator.clear();
	bAccumulator.clear();
	tAccumulator.clear();
}

void mainRHT(Mat& image, float* ellRes) {
	srand(time(NULL));

	minA = 0.23 * image.cols;
	maxA = 0.54 * image.cols;
	minB = 0.16 * image.cols;
	maxB = 0.45 * image.cols;
	maxTetha = 180;

	std::vector<Titik> filteredPoints = getCandidatePoints(image);

	//Inisialisasi 5 akumulator 1D
	accumulatorSize = (image.cols > image.rows) ? image.cols : image.rows;
	xAccumulator = std::vector<int>(accumulatorSize);
	yAccumulator = std::vector<int>(accumulatorSize);
	aAccumulator = std::vector<int>(accumulatorSize);
	bAccumulator = std::vector<int>(accumulatorSize);
	tAccumulator = std::vector<int>(accumulatorSize);

	for (int i = 0; i < accumulatorSize; i++) {
		xAccumulator[i] = 0;
		yAccumulator[i] = 0;
		aAccumulator[i] = 0;
		bAccumulator[i] = 0;
		tAccumulator[i] = 0;
	}
	highest.a = 0;
	highest.b = 0;
	highest.xc = 0;
	highest.yc = 0;
	highest.theta = 0;
	int samplingLimit = filteredPoints.size();
	for (int i = 0; i < samplingLimit; i++) {
		bool isValidParams = false;
		EllipseParams params;
		while (!isValidParams) {
			getRandomPoints(filteredPoints, sampleNumber);
			params = solveEllipseMatrixEquation();

			if (params.a > 0 && params.b > 0 && params.xc > 0
					&& params.yc > 0) {
				isValidParams = true;
			}
		}
		float ecc = ((float) params.b) / params.a;
		if (ecc <= MAX_ECC && ecc >= MIN_ECC && params.a <= maxA
				&& params.a >= minA && params.b <= maxB && params.b >= minB
				&& params.xc > -1 && params.yc > -1
				&& params.xc < accumulatorSize && params.yc < accumulatorSize) {

			if (params.theta < 0) {
				params.theta += 360;
			}
			aAccumulator[params.a] += 1;
			bAccumulator[params.b] += 1;
			xAccumulator[params.xc] += 1;
			yAccumulator[params.yc] += 1;
			tAccumulator[params.theta] += 1;

			if (aAccumulator[highest.a] < aAccumulator[params.a]) {
				highest.a = params.a;
			}
			if (bAccumulator[highest.b] < bAccumulator[params.b]) {
				highest.b = params.b;
			}
			if (xAccumulator[highest.xc] < xAccumulator[params.xc]) {
				highest.xc = params.xc;
			}
			if (yAccumulator[highest.yc] < yAccumulator[params.yc]) {
				highest.yc = params.yc;
			}
			if (tAccumulator[highest.theta] < tAccumulator[params.b]) {
				highest.theta = params.theta;
			}
		}
	}
	ellRes[0] = (float) highest.xc;
	ellRes[1] = (float) highest.yc;
	ellRes[2] = (float) highest.a;
	ellRes[3] = (float) highest.b;
	ellRes[4] = (float) highest.theta;
	ellRes[5] = -1;
	ellipse(image, Point(highest.xc, highest.yc), Size(highest.a, highest.b),
			highest.theta, 0, 360, Scalar(255, 0, 0), 1, 8, 0);
	filteredPoints.clear();
	xAccumulator.clear();
	yAccumulator.clear();
	aAccumulator.clear();
	bAccumulator.clear();
	tAccumulator.clear();

}

void find_center(double a, double b, double tetha, double x_1, double y_1,
		double x_2, double y_2) {
	double x1 = x_1 * cos(tetha) - y_1 * sin(tetha);
	double x2 = x_2 * cos(tetha) - y_2 * sin(tetha);
	double y1 = x_1 * sin(tetha) + y_1 * cos(tetha);
	double y2 = x_2 * sin(tetha) + y_2 * cos(tetha);

	//printf ("x1=%f, y1=%f , x2=%f, y2=%f\n",x1,y1,x2,y2);
	double F = (1 / (a * a));
	double G = (1 / (b * b));

	//printf("G=%f,F=%f\n",G,F);

	double R = ((G * (y1 - y2)) / (F * (x1 - x2)));
	double Q = ((x1 * x1 - x2 * x2) / (2 * x1 - 2 * x2))
			+ ((G * (y1 * y1 - y2 * y2)) / (2 * F * x1 - 2 * F * x2));

	double A = (F * R * R + G);
	double B = (2 * x1 * R * F - 2 * Q * R * F - 2 * G * y1);
	double C = (x1 * x1 * F - 2 * x1 * Q * F + F * Q * Q + G * y1 * y1 - 1);

	//printf("R=%f, Q=%f\n",R,Q);
	//printf("A=%f, B=%f, C=%f\n",A,B,C);

	double down = 2.0 * A;
	//printf("Nilai down = %f\n",down);
	double k_pos = (-1 * B + sqrt(B * B - 4 * A * C)) / down;
	double k_neg = (-1 * B - sqrt(B * B - 4 * A * C)) / down;
	double h_pos = Q - R * k_pos;
	double h_neg = Q - R * k_neg;

	centerPoints[0].x = h_pos * cos(-tetha) - k_pos * sin(-tetha);
	centerPoints[1].x = h_neg * cos(-tetha) - k_neg * sin(-tetha);
	centerPoints[0].y = h_pos * sin(-tetha) + k_pos * cos(-tetha);
	centerPoints[1].y = h_neg * sin(-tetha) + k_neg * cos(-tetha);
}

void HTEllipse(Mat& inputimage, Mat& accumulator, std::vector<Titik>& points,
		Particle& par) {
	double a = par.a;
	double b = par.b;
	double theta = -1 * par.sudut * M_PI / 180.0;

	Titik p[2];
	Titik t;
	int prevIndex = 0;
	int pointCounter = points.size();
	int columns = inputimage.cols;
	int rows = inputimage.rows;
	accumulator = accumulator * 0;
	double best = -1, x0 = 0, y0 = 0;
	for (int i = 0; i < SAMPLING_POINT; i++) {
		int index;
		int j;
		//cari titik secara random, hingga menemukan titik center yang valid.
		for (j = 0; j < N_TRY_MID_POINT; j++) {

			do {
				index = rand() % pointCounter;
				t = points[index];
			} while (!t.isActive);

			prevIndex = index;
			p[0].x = t.x;
			p[0].y = t.y;

			while (index == prevIndex || !t.isActive) {
				index = rand() % pointCounter;
				t = points[index];
			}
			p[1].x = t.x;
			p[1].y = t.y;

			find_center(a, b, theta, p[0].x, p[0].y, p[1].x, p[1].y);

			if ((centerPoints[0].x < columns && centerPoints[0].x >= 0
					&& centerPoints[0].y < rows && centerPoints[0].y >= 0)
					|| (centerPoints[1].x < columns && centerPoints[1].x >= 0
							&& centerPoints[1].y < rows
							&& centerPoints[1].y >= 0)) {
				break;
			}
		}

		if (j == N_TRY_MID_POINT) {
			//No valid ellipse center found
			//?
		}
		//output validEllipse = voted center on image save on accumulator
		if (centerPoints[0].x < columns && centerPoints[0].x >= 0
				&& centerPoints[0].y < rows && centerPoints[0].y >= 0) {
			int x1 = floor(centerPoints[0].y);
			int y1 = floor(centerPoints[0].x);
			accumulator.at<float>(x1, y1) += 1;
			if (accumulator.at<float>(x1, y1) > best) {
				best = accumulator.at<float>(x1, y1);
				x0 = x1;
				y0 = y1;
			}
		}
		if (centerPoints[1].x < columns && centerPoints[1].x >= 0
				&& centerPoints[1].y < rows && centerPoints[1].y >= 0) {
			int x2 = floor(centerPoints[1].y);
			int y2 = floor(centerPoints[1].x);
			accumulator.at<float>(x2, y2) += 1;
			if (accumulator.at<float>(x2, y2) > best) {
				best = accumulator.at<float>(x2, y2);
				x0 = x2;
				y0 = y2;
			}
		}

	}
	htResult.x = y0;
	htResult.y = x0;
	htResult.fit = best;
}

void iteratePSO(Mat& image, Mat& accumulator, std::vector<Titik>& points,
		int indexIteration) {

	for (int i = 0; i < N_PARTICLE; i++) {
		HTEllipse(image, accumulator, points, population[i]);
		population[i].fitness.x = htResult.x;
		population[i].fitness.y = htResult.y;
		population[i].fitness.fit = htResult.fit;

		if (population[i].fitness.fit > population[i].lbest.fit) {
			population[i].lbest.a = population[i].a;
			population[i].lbest.b = population[i].b;
			population[i].lbest.sudut = population[i].sudut;
			population[i].lbest.x = population[i].fitness.x;
			population[i].lbest.y = population[i].fitness.y;
			population[i].lbest.fit = population[i].fitness.fit;
		}
		if (population[i].lbest.fit > globalBest.fitness.fit) {
			globalBest = population[i];
			bestIndex = i;
		}
	}

	for (int i = 0; i < N_PARTICLE; i++) {
		if (bestIndex != i) {
			LocalBest lbest = population[i].lbest;
			Velocity prevV = population[i].prevV;
			Velocity newV;
			newV.a = prevV.a + C1 * acak() * (lbest.a - population[i].a)
					+ C2 * acak() * (globalBest.a - population[i].a);
			newV.b = prevV.b + C1 * acak() * (lbest.b - population[i].b)
					+ C2 * acak() * (globalBest.b - population[i].b);
			newV.sudut = prevV.sudut
					+ C1 * acak() * (lbest.sudut - population[i].sudut)
					+ C2 * acak() * (globalBest.sudut - population[i].sudut);

			population[i].a += newV.a;
			population[i].b += newV.b;
			population[i].sudut += newV.sudut;

			if (population[i].a < minA) {
				population[i].a = minA;
			}
			if (population[i].a > maxA) {
				population[i].a = maxA;
			}

			if (ECCENTRIC_MODE) {
				float rMin = MIN_ECC * population[i].a;
				float rMax = MAX_ECC * population[i].a;
				if (population[i].b < rMin) {
					population[i].b = rMin;
				}
				if (population[i].b > rMax) {
					population[i].b = rMax;
				}

			} else {
				if (population[i].b < minB) {
					population[i].b = minB;
				}
				if (population[i].b > maxB) {
					population[i].b = maxB;
				}
			}

			population[i].sudut = (int) population[i].sudut % 180;
			population[i].prevV = newV;
		}
	}
}

void mainPSO(Mat& image, float* ellRes) {
	srand(time(NULL));

	int columns = image.cols;
	int rows = image.rows;

	Mat accumulator(rows, columns, CV_32FC1);

	std::vector<Titik> points = getCandidatePoints(image);
	SAMPLING_POINT = 0.5 * points.size();

	globalBest.fitness.fit = -1;

	int layer = 1;

	minA = columns * 0.23;
	maxA = columns * 0.54;
	segA = (maxA - minA) / (layer);
	minB = columns * 0.16;
	maxB = columns * 0.45;
	segB = (maxB - minB) / (layer);
	minTetha = 0;
	maxTetha = 180;
	segTetha = (maxTetha - minTetha) / (N_PARTICLE / layer + 1); //36??

	for (int i = 0; i < N_PARTICLE; ++i) {
		bool isValidParams = false;
		EllipseParams paramElipse;
		while (!isValidParams) {
			getRandomPoints(points, 5);
			paramElipse = solveEllipseMatrixEquation();

			//tidak ada parameter negatif
			if (paramElipse.a > 0 && paramElipse.b > 0 && paramElipse.xc > 0
					&& paramElipse.yc > 0 && paramElipse.a < maxA) {
				isValidParams = true;
			}
		}
		float ecc = paramElipse.b / paramElipse.a;
		if (ECCENTRIC_MODE) {
			if (ecc <= MAX_ECC && ecc >= MIN_ECC) {
				population[i].a = paramElipse.a;
				population[i].b = paramElipse.b;

			} else {
				population[i].a = paramElipse.a;
				float r3 = MIN_ECC
						+ (float) rand()
								/ ((float) RAND_MAX / (MAX_ECC - MIN_ECC));
				population[i].b = r3 * population[i].a;
			}

		} else {
			population[i].a = paramElipse.a;
			population[i].b = paramElipse.b;
		}
		population[i].sudut = paramElipse.theta;
		population[i].lbest.a = population[i].a;
		population[i].lbest.b = population[i].b;
		population[i].lbest.sudut = population[i].sudut;
		population[i].lbest.fit = 0.;

	}

	for (int i = 0; i < N_ITERATION; i++) {
//		sprintf(aa, "Start %d", i);
//		LOGD(aa);
		iteratePSO(image, accumulator, points, i);
	}
	ellRes[0] = globalBest.fitness.x;
	ellRes[1] = globalBest.fitness.y;
	ellRes[2] = globalBest.a;
	ellRes[3] = globalBest.b;
	ellRes[4] = globalBest.sudut;
	ellRes[5] = -1;
	ellipse(image, Point(globalBest.fitness.x, globalBest.fitness.y),
			Size(globalBest.a, globalBest.b), globalBest.sudut, 0, 360,
			Scalar(255, 0, 0), 1, 8, 0);
	points.clear();
}

