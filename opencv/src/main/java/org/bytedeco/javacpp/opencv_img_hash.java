// Targeted by JavaCPP version 1.4.4: DO NOT EDIT THIS FILE

package org.bytedeco.javacpp;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class opencv_img_hash extends org.bytedeco.javacpp.presets.opencv_img_hash {
    static { Loader.load(); }

// Parsed from <opencv2/img_hash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_IMG_HASH_H
// #define OPENCV_IMG_HASH_H

// #include "opencv2/img_hash/average_hash.hpp"
// #include "opencv2/img_hash/block_mean_hash.hpp"
// #include "opencv2/img_hash/color_moment_hash.hpp"
// #include "opencv2/img_hash/marr_hildreth_hash.hpp"
// #include "opencv2/img_hash/phash.hpp"
// #include "opencv2/img_hash/radial_variance_hash.hpp"

/**
\defgroup img_hash The module brings implementations of different image hashing algorithms.
<p>
Provide algorithms to extract the hash of images and fast way to figure out most similar images in
huge data set.
<p>
Namespace for all functions is cv::img_hash.
<p>
### Supported Algorithms
<p>
- Average hash (also called Different hash)
- PHash (also called Perceptual hash)
- Marr Hildreth Hash
- Radial Variance Hash
- Block Mean Hash (modes 0 and 1)
- Color Moment Hash (this is the one and only hash algorithm resist to rotation attack(-90~90 degree))
<p>
You can study more about image hashing from following paper and websites:
<p>
- "Implementation and benchmarking of perceptual image hash functions" \cite zauner2010implementation
- "Looks Like It" \cite lookslikeit
<p>
### Code Example
<p>
\include samples/hash_samples.cpp
<p>
### Performance under different attacks
<p>
![Performance chart](img_hash/doc/attack_performance.JPG)
<p>
### Speed comparison with PHash library (100 images from ukbench)
<p>
![Hash Computation chart](img_hash/doc/hash_computation_chart.JPG)
![Hash comparison chart](img_hash/doc/hash_comparison_chart.JPG)
<p>
As you can see, hash computation speed of img_hash module outperform [PHash library](http://www.phash.org/) a lot.
<p>
PS : I do not list out the comparison of Average hash, PHash and Color Moment hash, because I cannot
find them in PHash.
<p>
### Motivation
<p>
Collects useful image hash algorithms into opencv, so we do not need to rewrite them by ourselves
again and again or rely on another 3rd party library(ex : PHash library). BOVW or correlation
matching are good and robust, but they are very slow compare with image hash, if you need to deal
with large scale CBIR(content based image retrieval) problem, image hash is a more reasonable
solution.
<p>
### More info
<p>
You can learn more about img_hash modules from following links, these links show you how to find
similar image from ukbench dataset, provide thorough benchmark of different attacks(contrast, blur,
noise(gaussion,pepper and salt), jpeg compression, watermark, resize).
<p>
* [Introduction to image hash module of opencv](http://qtandopencv.blogspot.my/2016/06/introduction-to-image-hash-module-of.html)
* [Speed up image hashing of opencv(img_hash) and introduce color moment hash](http://qtandopencv.blogspot.my/2016/06/speed-up-image-hashing-of-opencvimghash.html)
<p>
### Contributors
<p>
Tham Ngap Wei, thamngapwei\gmail.com
<p>
*/

// #endif // OPENCV_IMG_HASH_H


// Parsed from <opencv2/img_hash/img_hash_base.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_IMG_HASH_BASE_HPP
// #define OPENCV_IMG_HASH_BASE_HPP

// #include "opencv2/core.hpp"

/** \addtogroup img_hash
 *  \{
<p>
/** \brief The base class for image hash algorithms
 */
@Namespace("cv::img_hash") @NoOffset public static class ImgHashBase extends Algorithm {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public ImgHashBase(Pointer p) { super(p); }

    @Opaque public static class ImgHashImpl extends Pointer {
        /** Empty constructor. Calls {@code super((Pointer)null)}. */
        public ImgHashImpl() { super((Pointer)null); }
        /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
        public ImgHashImpl(Pointer p) { super(p); }
    }
    /** \brief Computes hash of the input image
        @param inputArr input image want to compute hash value
        @param outputArr hash of the image
    */
    public native void compute(@ByVal Mat inputArr, @ByVal Mat outputArr);
    public native void compute(@ByVal UMat inputArr, @ByVal UMat outputArr);
    public native void compute(@ByVal GpuMat inputArr, @ByVal GpuMat outputArr);
    /** \brief Compare the hash value between inOne and inTwo
        @param hashOne Hash value one
        @param hashTwo Hash value two
        @return value indicate similarity between inOne and inTwo, the meaning
        of the value vary from algorithms to algorithms
    */
    public native double compare(@ByVal Mat hashOne, @ByVal Mat hashTwo);
    public native double compare(@ByVal UMat hashOne, @ByVal UMat hashTwo);
    public native double compare(@ByVal GpuMat hashOne, @ByVal GpuMat hashTwo);
}

/** \} */

  // cv::img_hash::

// #endif // OPENCV_IMG_HASH_BASE_HPP


// Parsed from <opencv2/img_hash/average_hash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_AVERAGE_HASH_HPP
// #define OPENCV_AVERAGE_HASH_HPP

// #include "img_hash_base.hpp"

/** \addtogroup img_hash
 *  \{
<p>
/** \brief Computes average hash value of the input image
<p>
This is a fast image hashing algorithm, but only work on simple case. For more details, please
refer to \cite lookslikeit
*/
@Namespace("cv::img_hash") public static class AverageHash extends ImgHashBase {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public AverageHash(Pointer p) { super(p); }

    public static native @Ptr AverageHash create();
}

/** \brief Calculates img_hash::AverageHash in one call
@param inputArr input image want to compute hash value, type should be CV_8UC4, CV_8UC3 or CV_8UC1.
@param outputArr Hash value of input, it will contain 16 hex decimal number, return type is CV_8U
*/
@Namespace("cv::img_hash") public static native void averageHash(@ByVal Mat inputArr, @ByVal Mat outputArr);
@Namespace("cv::img_hash") public static native void averageHash(@ByVal UMat inputArr, @ByVal UMat outputArr);
@Namespace("cv::img_hash") public static native void averageHash(@ByVal GpuMat inputArr, @ByVal GpuMat outputArr);

/** \} */

 // cv::img_hash::

// #endif // OPENCV_AVERAGE_HASH_HPP


// Parsed from <opencv2/img_hash/block_mean_hash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_BLOCK_MEAN_HASH_HPP
// #define OPENCV_BLOCK_MEAN_HASH_HPP

// #include "img_hash_base.hpp"

/** \addtogroup img_hash
 *  \{ */

/** enum cv::img_hash::BlockMeanHashMode */
public static final int
    /** use fewer block and generate 16*16/8 uchar hash value */
    BLOCK_MEAN_HASH_MODE_0 = 0,
    /** use block blocks(step sizes/2), generate 31*31/8 + 1 uchar hash value */
    BLOCK_MEAN_HASH_MODE_1 = 1;

/** \brief Image hash based on block mean.
<p>
See \cite zauner2010implementation for details.
*/
@Namespace("cv::img_hash") public static class BlockMeanHash extends ImgHashBase {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public BlockMeanHash(Pointer p) { super(p); }

    /** \brief Create BlockMeanHash object
        @param mode
    */
    public native void setMode(int mode);
    public native @StdVector DoublePointer getMean();
    public static native @Ptr BlockMeanHash create(int mode/*=cv::img_hash::BLOCK_MEAN_HASH_MODE_0*/);
    public static native @Ptr BlockMeanHash create();
}

/** \brief Computes block mean hash of the input image
    @param inputArr input image want to compute hash value, type should be CV_8UC4, CV_8UC3 or CV_8UC1.
    @param outputArr Hash value of input, it will contain 16 hex decimal number, return type is CV_8U
    @param mode
*/
@Namespace("cv::img_hash") public static native void blockMeanHash(@ByVal Mat inputArr,
                                @ByVal Mat outputArr,
                                int mode/*=cv::img_hash::BLOCK_MEAN_HASH_MODE_0*/);
@Namespace("cv::img_hash") public static native void blockMeanHash(@ByVal Mat inputArr,
                                @ByVal Mat outputArr);
@Namespace("cv::img_hash") public static native void blockMeanHash(@ByVal UMat inputArr,
                                @ByVal UMat outputArr,
                                int mode/*=cv::img_hash::BLOCK_MEAN_HASH_MODE_0*/);
@Namespace("cv::img_hash") public static native void blockMeanHash(@ByVal UMat inputArr,
                                @ByVal UMat outputArr);
@Namespace("cv::img_hash") public static native void blockMeanHash(@ByVal GpuMat inputArr,
                                @ByVal GpuMat outputArr,
                                int mode/*=cv::img_hash::BLOCK_MEAN_HASH_MODE_0*/);
@Namespace("cv::img_hash") public static native void blockMeanHash(@ByVal GpuMat inputArr,
                                @ByVal GpuMat outputArr);

/** \} */

 // cv::img_hash::

// #endif // OPENCV_BLOCK_MEAN_HASH_HPP


// Parsed from <opencv2/img_hash/color_moment_hash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_COLOR_MOMENT_HASH_HPP
// #define OPENCV_COLOR_MOMENT_HASH_HPP

// #include "img_hash_base.hpp"

/** \addtogroup img_hash
 *  \{
<p>
/** \brief Image hash based on color moments.
<p>
See \cite tang2012perceptual for details.
*/
@Namespace("cv::img_hash") public static class ColorMomentHash extends ImgHashBase {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public ColorMomentHash(Pointer p) { super(p); }

    public static native @Ptr ColorMomentHash create();
}

/** \brief Computes color moment hash of the input, the algorithm
    is come from the paper "Perceptual  Hashing  for  Color  Images
    Using  Invariant Moments"
    @param inputArr input image want to compute hash value,
    type should be CV_8UC4, CV_8UC3 or CV_8UC1.
    @param outputArr 42 hash values with type CV_64F(double)
     */
@Namespace("cv::img_hash") public static native void colorMomentHash(@ByVal Mat inputArr, @ByVal Mat outputArr);
@Namespace("cv::img_hash") public static native void colorMomentHash(@ByVal UMat inputArr, @ByVal UMat outputArr);
@Namespace("cv::img_hash") public static native void colorMomentHash(@ByVal GpuMat inputArr, @ByVal GpuMat outputArr);

/** \} */

 // cv::img_hash::

// #endif // OPENCV_COLOR_MOMENT_HASH_HPP


// Parsed from <opencv2/img_hash/marr_hildreth_hash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_MARR_HILDRETH_HASH_HPP
// #define OPENCV_MARR_HILDRETH_HASH_HPP

// #include "img_hash_base.hpp"

/** \addtogroup img_hash
 *  \{
<p>
/** \brief Marr-Hildreth Operator Based Hash, slowest but more discriminative.
<p>
See \cite zauner2010implementation for details.
*/
@Namespace("cv::img_hash") public static class MarrHildrethHash extends ImgHashBase {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public MarrHildrethHash(Pointer p) { super(p); }

    /**
     * \brief self explain
     */
    public native float getAlpha();

    /**
     * \brief self explain
     */
    public native float getScale();

    /** \brief Set Mh kernel parameters
        @param alpha int scale factor for marr wavelet (default=2).
        @param scale int level of scale factor (default = 1)
    */
    public native void setKernelParam(float alpha, float scale);

    /**
        @param alpha int scale factor for marr wavelet (default=2).
        @param scale int level of scale factor (default = 1)
    */
    public static native @Ptr MarrHildrethHash create(float alpha/*=2.0f*/, float scale/*=1.0f*/);
    public static native @Ptr MarrHildrethHash create();
}

/** \brief Computes average hash value of the input image
    @param inputArr input image want to compute hash value,
    type should be CV_8UC4, CV_8UC3, CV_8UC1.
    @param outputArr Hash value of input, it will contain 16 hex
    decimal number, return type is CV_8U
    @param alpha int scale factor for marr wavelet (default=2).
    @param scale int level of scale factor (default = 1)
*/
@Namespace("cv::img_hash") public static native void marrHildrethHash(@ByVal Mat inputArr,
                                   @ByVal Mat outputArr,
                                   float alpha/*=2.0f*/, float scale/*=1.0f*/);
@Namespace("cv::img_hash") public static native void marrHildrethHash(@ByVal Mat inputArr,
                                   @ByVal Mat outputArr);
@Namespace("cv::img_hash") public static native void marrHildrethHash(@ByVal UMat inputArr,
                                   @ByVal UMat outputArr,
                                   float alpha/*=2.0f*/, float scale/*=1.0f*/);
@Namespace("cv::img_hash") public static native void marrHildrethHash(@ByVal UMat inputArr,
                                   @ByVal UMat outputArr);
@Namespace("cv::img_hash") public static native void marrHildrethHash(@ByVal GpuMat inputArr,
                                   @ByVal GpuMat outputArr,
                                   float alpha/*=2.0f*/, float scale/*=1.0f*/);
@Namespace("cv::img_hash") public static native void marrHildrethHash(@ByVal GpuMat inputArr,
                                   @ByVal GpuMat outputArr);

/** \} */

 // cv::img_hash::

// #endif // OPENCV_MARR_HILDRETH_HASH_HPP


// Parsed from <opencv2/img_hash/phash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_PHASH_HPP
// #define OPENCV_PHASH_HPP

// #include "img_hash_base.hpp"

/** \addtogroup img_hash
 *  \{
<p>
/** \brief pHash
<p>
Slower than average_hash, but tolerant of minor modifications
<p>
This algorithm can combat more variation than averageHash, for more details please refer to \cite lookslikeit
*/
@Namespace("cv::img_hash") public static class PHash extends ImgHashBase {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public PHash(Pointer p) { super(p); }

    public static native @Ptr PHash create();
}

/** \brief Computes pHash value of the input image
    @param inputArr input image want to compute hash value,
     type should be CV_8UC4, CV_8UC3, CV_8UC1.
    @param outputArr Hash value of input, it will contain 8 uchar value
*/
@Namespace("cv::img_hash") public static native void pHash(@ByVal Mat inputArr, @ByVal Mat outputArr);
@Namespace("cv::img_hash") public static native void pHash(@ByVal UMat inputArr, @ByVal UMat outputArr);
@Namespace("cv::img_hash") public static native void pHash(@ByVal GpuMat inputArr, @ByVal GpuMat outputArr);

/** \} */

  // cv::img_hash::

// #endif // OPENCV_PHASH_HPP


// Parsed from <opencv2/img_hash/radial_variance_hash.hpp>

// This file is part of OpenCV project.
// It is subject to the license terms in the LICENSE file found in the top-level directory
// of this distribution and at http://opencv.org/license.html.

// #ifndef OPENCV_RADIAL_VARIANCE_HASH_HPP
// #define OPENCV_RADIAL_VARIANCE_HASH_HPP

// #include "img_hash_base.hpp"

/** \addtogroup img_hash
 *  \{
<p>
<p>
/** \brief Image hash based on Radon transform.
<p>
See \cite tang2012perceptual for details.
*/
@Namespace("cv::img_hash") public static class RadialVarianceHash extends ImgHashBase {
    static { Loader.load(); }
    /** Pointer cast constructor. Invokes {@link Pointer#Pointer(Pointer)}. */
    public RadialVarianceHash(Pointer p) { super(p); }

    public static native @Ptr RadialVarianceHash create(double sigma/*=1*/, int numOfAngleLine/*=180*/);
    public static native @Ptr RadialVarianceHash create();

    public native int getNumOfAngleLine();
    public native double getSigma();

    public native void setNumOfAngleLine(int value);
    public native void setSigma(double value);

    // internals
    public native @StdVector DoublePointer getFeatures();
    public native @ByVal Mat getHash();
    public native @ByVal Mat getPixPerLine(@Const @ByRef Mat input);
    public native @ByVal Mat getProjection();
}

/** \brief Computes radial variance hash of the input image
    @param inputArr input image want to compute hash value,
    type should be CV_8UC4, CV_8UC3, CV_8UC1.
    @param outputArr Hash value of input
    @param sigma Gaussian kernel standard deviation
    @param numOfAngleLine The number of angles to consider
     */
@Namespace("cv::img_hash") public static native void radialVarianceHash(@ByVal Mat inputArr,
                                     @ByVal Mat outputArr,
                                     double sigma/*=1*/,
                                     int numOfAngleLine/*=180*/);
@Namespace("cv::img_hash") public static native void radialVarianceHash(@ByVal Mat inputArr,
                                     @ByVal Mat outputArr);
@Namespace("cv::img_hash") public static native void radialVarianceHash(@ByVal UMat inputArr,
                                     @ByVal UMat outputArr,
                                     double sigma/*=1*/,
                                     int numOfAngleLine/*=180*/);
@Namespace("cv::img_hash") public static native void radialVarianceHash(@ByVal UMat inputArr,
                                     @ByVal UMat outputArr);
@Namespace("cv::img_hash") public static native void radialVarianceHash(@ByVal GpuMat inputArr,
                                     @ByVal GpuMat outputArr,
                                     double sigma/*=1*/,
                                     int numOfAngleLine/*=180*/);
@Namespace("cv::img_hash") public static native void radialVarianceHash(@ByVal GpuMat inputArr,
                                     @ByVal GpuMat outputArr);


/** \} */

 // cv::img_hash::

// #endif // OPENCV_RADIAL_VARIANCE_HASH_HPP


}
