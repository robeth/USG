LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

include ${OCVAndroid}/sdk/native/jni/OpenCV.mk

LOCAL_MODULE    := mixed_sample
LOCAL_SRC_FILES := jni_part.cpp method.cpp
LOCAL_LDLIBS +=  -llog -ldl
LOCAL_CFLAGS += -std=c++11

include $(BUILD_SHARED_LIBRARY)
