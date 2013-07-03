/*
 * RHT.cpp
 *
 *  Created on: Mar 21, 2013
 *      Author: maviosso
 */

#include <opencv2\opencv.hpp>
#include <opencv2\highgui\highgui.hpp>	//To display and save image
#include <math.h>	//To round, cos, sin, etc
#include <ctime>	//To random
#include <unordered_map>	//To implement hash as accumulator
#include <iostream>	//To enable cout
#include <dirent.h>	//To list directory
#include <iomanip>	//To setprecision of double output
using namespace cv;

/**
 * Lakukan aproksimasi elips berdasarkan 5 titik random
 * Steps:
 * 		a. Ambil 5 pixels secara random
 * 		b. Aproksimasi parameter elips
 * 			- Lakukan inverse
 * 			- Temukan x center dan y center
 * 			- Temukan tan theta
 * 			- Temukan J,G,K sebagai modal major dan minor axis
 *
 */

typedef struct {
	int xc, yc, theta, a, b;
} EllipseParams;

typedef struct {
	double x, y;
	bool isActive;
} Titik;

const double PI = 3.141592;

int sampleNumber = 5;
int filteredCount;
int ITERATION, INTERVAL;
float DELTA_RECT;
uchar candidateThreshold = 128;
float minA, maxA, minB, maxB;
int maxTetha;
bool ECCENTRIC_MODE = true;
float MIN_ECC = 0.7;
float MAX_ECC = 0.9;
EllipseParams highest;
int accumulatorSize = 0;
std::vector<int> xAccumulator;
std::vector<int> yAccumulator;
std::vector<int> aAccumulator;
std::vector<int> bAccumulator;
std::vector<int> tAccumulator;

double convertRadToDegree(double rad) {
	return rad * 180 / PI;
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
	int counter = 0;
	int pointCounter = points.size();

	Titik temp;
	for (int i = 0; i < pointCounter; i++) {
		temp = points[i];
		if (rectMask.at<uchar>(temp.y, temp.x) == 0 && temp.isActive) {
			temp.isActive = false;
			counter++;
		}
	}
}

void eliminatePoint(Mat& image, Mat& rect, std::vector<Titik>& points, float xc,
		float yc, float a, float b, float angle) {
	std::cout << "Entered eliminate Point" << std::endl;
	rect *= 0;

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

std::vector<Titik> getRandomPoints(std::vector<Titik>& points, int number,
		Mat& image) {
	std::vector<Titik> result;
	std::vector<int> indexes;
	int indexed = 0;
	int randomInt;
	Titik temp;
	while (indexed < number) {
		do {
			randomInt = rand() % filteredCount;
			temp = points[randomInt];
		} while (!temp.isActive
				|| std::find(indexes.begin(), indexes.end(), randomInt)
						!= indexes.end());
		indexes.push_back(randomInt);
		result.push_back(temp);
		//std::cout << randomInt << ".p:" << points[randomInt] << "  so the size is "<< result.size() <<std::endl ;
		//circle(image, points[randomInt], 2, cvScalar(0, 0, 255, 255), 2, 8, 0);
		indexed++;
	}

	return result;
}

void debugFloatArray(Mat a) {
	//std::cout << a << std::endl << std::endl;
}

EllipseParams solveEllipseMatrixEquation(Mat& image,
		std::vector<Titik>& points) {
	float a[5][5];
	float b[5][1];
	Titik temp;
	for (int i = 0; i < sampleNumber; i++) {
		temp = points[i];
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

void mainIRHT(Mat& inputMat, int rhtIteration, int rhtInterval, float deltaRect,
	float* ellRes) {
//	srand(time(NULL));
//	ITERATION = rhtIteration;
//	INTERVAL = rhtInterval;
//	DELTA_RECT = deltaRect;
//
//	Mat image = inputMat;
//	Mat rect(image.rows, image.cols, CV_8UC1);
//	minA = 0.125 * image.cols;
//	maxA = 0.5 * image.cols;
//	minB = 0.125 * image.rows;
//	maxB = 0.5 * image.rows;
//	maxTetha = 180;
//
//	std::vector<Titik> filteredPoints = getCandidatePoints(image);
//
//	//Inisialisasi 5 akumulator 1D
//
//	accumulatorSize = (image.cols > image.rows) ? image.cols : image.rows;
//	xAccumulator = std::vector<int>(accumulatorSize);
//	yAccumulator = std::vector<int>(accumulatorSize);
//	aAccumulator = std::vector<int>(accumulatorSize);
//	bAccumulator = std::vector<int>(accumulatorSize);
//	tAccumulator = std::vector<int>(accumulatorSize);
//
//	for (int i = 0; i < accumulatorSize; i++) {
//		xAccumulator[i] = 0;
//		yAccumulator[i] = 0;
//		aAccumulator[i] = 0;
//		bAccumulator[i] = 0;
//		tAccumulator[i] = 0;
//	}
//	highest.a = 0;
//	highest.b = 0;
//	highest.xc = 0;
//	highest.yc = 0;
//	highest.theta = 0;
//
//	for (int i = 0; i < ITERATION; i++) {
//		//std::cout << "Iteration : " << i << std::endl;
//		bool isValidParams = false;
//		EllipseParams params;
//		while (!isValidParams) {
//			std::vector<Titik> chosenPoints = getRandomPoints(filteredPoints,
//					sampleNumber, image);
//			params = solveEllipseMatrixEquation(image, chosenPoints);
//
//			//tidak ada parameter negatif//KENAPA KOK NGA DI BATASIN DI SINI AJA SIH BERT?????
//			if (params.a > 0 && params.b > 0 && params.xc > 0
//					&& params.yc > 0) {
//				isValidParams = true;
//			}
//		}
//		float ecc = ((float) params.b) / params.a;
//		if (ecc <= MAX_ECC && ecc >= MIN_ECC && params.a <= maxA
//				&& params.a >= minA && params.b <= maxB && params.b >= minB
//				&& params.xc > -1 && params.yc > -1
//				&& params.xc < accumulatorSize && params.yc < accumulatorSize) {
//
//			if (params.theta < 0) {
//				params.theta += 360;
//			}
//
//			int aCounter = aAccumulator[params.a];
//			int bCounter = bAccumulator[params.b];
//			int xCounter = xAccumulator[params.xc];
//			int yCounter = yAccumulator[params.yc];
//			int tCounter = tAccumulator[params.theta];
//
//			aAccumulator[params.a] = aCounter + 1;
//			aAccumulator[params.b] = bCounter + 1;
//			aAccumulator[params.xc] = xCounter + 1;
//			aAccumulator[params.yc] = yCounter + 1;
//			aAccumulator[params.theta] = tCounter + 1;
//
//			if (aAccumulator[highest.a] < aCounter + 1) {
//				highest.a = params.a;
//			}
//			if (aAccumulator[highest.b] < bCounter + 1) {
//				highest.b = params.b;
//			}
//			if (aAccumulator[highest.xc] < xCounter + 1) {
//				highest.xc = params.xc;
//			}
//			if (aAccumulator[highest.yc] < yCounter + 1) {
//				highest.yc = params.yc;
//			}
//			if (aAccumulator[highest.theta] < tCounter + 1) {
//				highest.theta = params.theta;
//			}
//		}
//		if ((i + 1) % INTERVAL == 0) {
//			std::cout << "In" << std::endl;
//			eliminatePoint(image, rect, filteredPoints, highest.xc, highest.yc,
//					highest.a * 2 + DELTA_RECT, highest.b * 2 + DELTA_RECT,
//					highest.theta);
//		}
//	}
//	ellRes[0] = (float) highest.xc;
//	ellRes[1] = (float) highest.yc;
//	ellRes[2] = (float) highest.a;
//	ellRes[3] = (float) highest.b;
//	ellRes[4] = (float) highest.theta;
//	ellRes[5] = -1;
//	ellipse(image, Point(highest.xc, highest.yc), Size(highest.a, highest.b),
//			highest.theta, 0, 360, Scalar(255, 0, 0), 1, 8, 0);

}

void mainRHT(Mat& inputMat, int rhtIteration, float* ellRes) {
//	srand(time(NULL));
//	ITERATION = rhtIteration;
//
//	Mat image = inputMat;
//	Mat rect(image.rows, image.cols, CV_8UC1);
//	minA = 0.125 * image.cols;
//	maxA = 0.5 * image.cols;
//	minB = 0.125 * image.rows;
//	maxB = 0.5 * image.rows;
//	maxTetha = 180;
//
//	std::vector<Titik> filteredPoints = getCandidatePoints(image);
//
//	//Inisialisasi 5 akumulator 1D
//	accumulatorSize = (image.cols > image.rows) ? image.cols : image.rows;
//	xAccumulator = std::vector<int>(accumulatorSize);
//	yAccumulator = std::vector<int>(accumulatorSize);
//	aAccumulator = std::vector<int>(accumulatorSize);
//	bAccumulator = std::vector<int>(accumulatorSize);
//	tAccumulator = std::vector<int>(accumulatorSize);
//
//	for (int i = 0; i < accumulatorSize; i++) {
//		xAccumulator[i] = 0;
//		yAccumulator[i] = 0;
//		aAccumulator[i] = 0;
//		bAccumulator[i] = 0;
//		tAccumulator[i] = 0;
//	}
//	highest.a = 0;
//	highest.b = 0;
//	highest.xc = 0;
//	highest.yc = 0;
//	highest.theta = 0;
//
//	for (int i = 0; i < ITERATION; i++) {
//		//std::cout << "Iteration : " << i << std::endl;
//		bool isValidParams = false;
//		EllipseParams params;
//		while (!isValidParams) {
//			std::vector<Titik> chosenPoints = getRandomPoints(filteredPoints,
//					sampleNumber, image);
//			params = solveEllipseMatrixEquation(image, chosenPoints);
//
//			//tidak ada parameter negatif//KENAPA KOK NGA DI BATASIN DI SINI AJA SIH BERT?????
//			if (params.a > 0 && params.b > 0 && params.xc > 0
//					&& params.yc > 0) {
//				isValidParams = true;
//			}
//		}
//		float ecc = ((float) params.b) / params.a;
//		if (ecc <= MAX_ECC && ecc >= MIN_ECC && params.a <= maxA
//				&& params.a >= minA && params.b <= maxB && params.b >= minB
//				&& params.xc > -1 && params.yc > -1
//				&& params.xc < accumulatorSize && params.yc < accumulatorSize) {
//
//			if (params.theta < 0) {
//				params.theta += 360;
//			}
//
//			int aCounter = aAccumulator[params.a];
//			int bCounter = bAccumulator[params.b];
//			int xCounter = xAccumulator[params.xc];
//			int yCounter = yAccumulator[params.yc];
//			int tCounter = tAccumulator[params.theta];
//
//			aAccumulator[params.a] = aCounter + 1;
//			aAccumulator[params.b] = bCounter + 1;
//			aAccumulator[params.xc] = xCounter + 1;
//			aAccumulator[params.yc] = yCounter + 1;
//			aAccumulator[params.theta] = tCounter + 1;
//
//			if (aAccumulator[highest.a] < aCounter + 1) {
//				highest.a = params.a;
//			}
//			if (aAccumulator[highest.b] < bCounter + 1) {
//				highest.b = params.b;
//			}
//			if (aAccumulator[highest.xc] < xCounter + 1) {
//				highest.xc = params.xc;
//			}
//			if (aAccumulator[highest.yc] < yCounter + 1) {
//				highest.yc = params.yc;
//			}
//			if (aAccumulator[highest.theta] < tCounter + 1) {
//				highest.theta = params.theta;
//			}
//		}
//	}
//	ellRes[0] = (float) highest.xc;
//	ellRes[1] = (float) highest.yc;
//	ellRes[2] = (float) highest.a;
//	ellRes[3] = (float) highest.b;
//	ellRes[4] = (float) highest.theta;
//	ellRes[5] = -1;
//	ellipse(image, Point(highest.xc, highest.yc), Size(highest.a, highest.b),
//			highest.theta, 0, 360, Scalar(255, 0, 0), 1, 8, 0);

}

