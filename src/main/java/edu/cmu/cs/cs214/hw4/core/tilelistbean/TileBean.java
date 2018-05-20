// CHECKSTYLE:OFF
package edu.cmu.cs.cs214.hw4.core.tilelistbean;

public class TileBean {
	private String imageID;
	private SegmentBean[] segment;
	private RoadFeatureBean[] roadFeatures;
	private CityFeatureBean[] cityFeatures;
	private FarmFeatureBean[] farmFeatures;
	private CloisterFeatureBean[] cloisterFeature;

	public String getImageID() {
		return imageID;
	}
	
	public void setImageID(String imageID) {
		this.imageID = imageID;
	}
	
	public SegmentBean[] getSegment() {
		return segment.clone();
	}

	public void setSegment(SegmentBean[] segment) {
		this.segment = segment.clone();
	}

	public RoadFeatureBean[] getRoadFeature() {
		if (this.roadFeatures == null) {
			return null;
		}
		return roadFeatures.clone();
	}
	
	public void setRoadFeature(RoadFeatureBean[] roadFeatures) {
		this.roadFeatures = roadFeatures.clone();
	}
	
	public CityFeatureBean[] getCityFeature() {
		if (this.cityFeatures == null) {
			return null;
		}
		return cityFeatures.clone();
	}
	
	public void setCityFeature(CityFeatureBean[] cityFeatures) {
		this.cityFeatures = cityFeatures.clone();
	}
	
	public FarmFeatureBean[] getFarmFeature() {
		if (this.farmFeatures == null) {
			return null;
		}
		return farmFeatures.clone();
	}
	
	public void setFarmFeature(FarmFeatureBean[] farmFeatures) {
		this.farmFeatures = farmFeatures.clone();
	}
	
	public CloisterFeatureBean[] getCloisterFeature() {
		if (this.cloisterFeature == null) {
			return null;
		}
		return cloisterFeature.clone();
	}
	
	public void setCloisterFeature(CloisterFeatureBean[] cloisterFeature) {
		this.cloisterFeature = cloisterFeature.clone();
	}
}
