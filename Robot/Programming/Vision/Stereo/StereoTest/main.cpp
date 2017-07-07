/*
 * main.cpp
 *
 *  Created on: Mar 9, 2017
 *      Author: conner
 */

#include <iostream>
#include <pcl/visualization/pcl_visualizer.h>
#include <pcl/visualization/image_viewer.h>
#include <pcl/io/pcd_io.h>
#include <pcl/stereo/stereo_matching.h>
int main()
{
	pcl::PointCloud<pcl::RGB> left;
	pcl::PointCloud<pcl::RGB> right;
	pcl::PCDReader pcdReader;
	std::cout << "Reading left... ";
	std::cout.flush();
	if(pcdReader.read("left.pcd", left) == -1)
	{
		std::cout << "ERROR" << std::endl;
		std::cout << "Could not read file \"left.pcd\"." << std::endl;
		return 1;
	}
	else if(!left.isOrganized())
	{
		std::cout << "ERROR" << std::endl;
		std::cout << "File \"left.pcd\" is not organized." << std::endl;
		return 1;
	}
	std::cout << "OK" << std::endl;

	std::cout << "Reading right... ";
	std::cout.flush();
	if(pcdReader.read("right.pcd", right) == -1)
	{
		std::cout << "ERROR" << std::endl;
		std::cout << "Could not read file \"right.pcd\"." << std::endl;
		return 1;
	}
	else if(!right.isOrganized())
	{
		std::cout << "ERROR" << std::endl;
		std::cout << "File \"right.pcd\" is not organized." << std::endl;
		return 1;
	}
	std::cout << "OK" << std::endl;

	if(left.width != right.width || left.height != right.height)
	{
		std::cout << "ERROR" << std::endl;
		std::cout << "Image sizes do not match." << std::endl;
		return 2;
	}

	pcl::BlockBasedStereoMatching matcher;
	matcher.setMaxDisparity(60);
	matcher.setXOffset(0);
	matcher.setRadius(5);

	//only needed for AdaptiveCostSOStereoMatching:
	//matcher.setSmoothWeak(20);
	//matcher.setSmoothStrong(100);
	//matcher.setGammaC(25);
	//matcher.setGammaS(10);

	matcher.setRatioFilter(20);
	matcher.setPeakFilter(0);

	matcher.setLeftRightCheck(true);
	matcher.setLeftRightCheckThreshold(1);

	matcher.setPreProcessing(true);

	std::cout << "Computing image matches... ";
	std::cout.flush();
	matcher.compute(left, right);
	matcher.medianFilter(4);
	std::cout << "OK" << std::endl;

	std::cout << "Generating point cloud... ";
	std::cout.flush();
	pcl::PointCloud<pcl::PointXYZRGB>::Ptr output(new pcl::PointCloud<pcl::PointXYZRGB>);
	matcher.getPointCloud(318.112200, 224.334900, 368.534700, 0.8387445, output, left.makeShared());
	std::cout << "OK" << std::endl;

	std::cout << "Displaying..." << std::endl;
	pcl::PointCloud<pcl::RGB>::Ptr vmap( new pcl::PointCloud<pcl::RGB> );
	matcher.getVisualMap(vmap);

	pcl::visualization::ImageViewer iv ("My viewer");
	//iv.addRGBImage<pcl::RGB> (vmap);
	iv.addRGBImage<pcl::RGB> (left);

	boost::shared_ptr<pcl::visualization::PCLVisualizer> viewer (new pcl::visualization::PCLVisualizer ("3D Viewer"));

	pcl::visualization::PointCloudColorHandlerRGBField<pcl::PointXYZRGB> intensity(output);
	viewer->addPointCloud<pcl::PointXYZRGB> (output, intensity, "stereo");

	viewer->initCameraParameters ();
	//viewer->spin();
	while (!viewer->wasStopped ())
	{
		iv.spinOnce(100);
		viewer->spinOnce (100);
		boost::this_thread::sleep (boost::posix_time::microseconds (100000));
	}
}
