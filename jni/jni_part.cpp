#include <jni.h>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <ctime>
#include "method.h"



using namespace std;
using namespace cv;

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jfloatArray JNICALL Java_id_fruity_usg_UsgActivity_RHT(JNIEnv*,
		jobject, jlong addrM1);

JNIEXPORT jfloatArray JNICALL Java_id_fruity_usg_UsgActivity_IRHT(JNIEnv*,
		jobject, jlong addrM1);

JNIEXPORT jfloatArray JNICALL Java_id_fruity_usg_UsgActivity_PSO(JNIEnv*,
		jobject, jlong addrM1);

JNIEXPORT void JNICALL Java_id_fruity_usg_UsgActivity_Mark(JNIEnv*,
		jobject, jlong addrM1, jfloat, jfloat, jfloat, jfloat, jfloat);

JNIEXPORT jfloatArray JNICALL Java_id_fruity_usg_UsgActivity_RHT(JNIEnv *env,
		jobject, jlong addrM1) {
	Mat& mGr = *(Mat*) addrM1;
	clock_t start, end;
	start = clock();
	float ellipseResult[6];
	mainRHT(mGr, ellipseResult);
	end = clock();
	double runningClock = double(end - start) / CLOCKS_PER_SEC;
	ellipseResult[5] = runningClock;

	jfloatArray result;
	result = env->NewFloatArray(6);
	if (result == NULL) {
		return NULL; /* out of memory error thrown */
	}
	env->SetFloatArrayRegion(result, 0, 6, ellipseResult);

	return result;
}

JNIEXPORT jfloatArray JNICALL Java_id_fruity_usg_UsgActivity_IRHT(JNIEnv *env,
		jobject, jlong addrM1) {

	Mat& mGr = *(Mat*) addrM1;
	clock_t start, end;
	start = clock();
	float ellipseResult[6];
	mainIRHT(mGr, ellipseResult);
	end = clock();
	float runningClock = float(end - start) / CLOCKS_PER_SEC;
	ellipseResult[5] = runningClock;
	jfloatArray result;
	result = env->NewFloatArray(6);
	if (result == NULL) {
		return NULL; /* out of memory error thrown */
	}

	env->SetFloatArrayRegion(result, 0, 6, ellipseResult);

	return result;
}

JNIEXPORT jfloatArray JNICALL Java_id_fruity_usg_UsgActivity_PSO(JNIEnv *env,
		jobject, jlong addrM1) {
	Mat& mGr = *(Mat*) addrM1;
	clock_t start, end;
	start = clock();
	float ellipseResult[6];
	mainPSO(mGr, ellipseResult);
	end = clock();
	double runningClock = double(end - start) / CLOCKS_PER_SEC;
	ellipseResult[5] = runningClock;

	jfloatArray result;
	result = env->NewFloatArray(6);
	if (result == NULL) {
		return NULL; /* out of memory error thrown */
	}
	env->SetFloatArrayRegion(result, 0, 6, ellipseResult);

	return result;
}

JNIEXPORT void JNICALL Java_id_fruity_usg_UsgActivity_Mark(JNIEnv *env,
		jobject, jlong addrM1, jfloat a, jfloat b, jfloat x, jfloat y, jfloat theta){
	Mat& mGr = *(Mat*) addrM1;
	ellipse(mGr, Point(x,y), Size(a,b), theta, 0, 360, Scalar(255,0,0));
}

#ifdef __cplusplus
}
#endif
