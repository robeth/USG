package id.fruity.usg.experiment;

import id.fruity.usg.R;

import java.util.HashMap;

public class USGSet {
	private static HashMap<Integer, USGConstant> dataset;
	private static boolean inited = false;
	public static void init(){
		if(dataset == null){
			dataset = new HashMap<Integer, USGConstant>(20);
			inited = true;
		}
		dataset.clear();
		dataset.put(R.drawable.im13, new USGConstant(R.drawable.im13,"im13.jpg",19.86f,5.42f,  188f,305f,287.4f,229.4f,8.81f,0.2451f));
		dataset.put(R.drawable.im14, new USGConstant(R.drawable.im14,"im14.jpg",31.33f,8.92f,209f,219f,299f,248.7f,8.27f,0.36496f));
		dataset.put(R.drawable.im15, new USGConstant(R.drawable.im15,"im15.jpg",32.28f,9.14f,203f,253f,310.9f,253.5f,-21.1f,0.36496f));
		dataset.put(R.drawable.im16, new USGConstant(R.drawable.im16,"im16.jpg",24.49f,6.74f,199f,234f,304.6f,242.6f,14.4f,0.28571f));
		dataset.put(R.drawable.im17, new USGConstant(R.drawable.im17,"im17.jpg",24.74f,6.93f,178f,248f,297f,251.3f,-27.2f,0.28571f));
		dataset.put(R.drawable.im18, new USGConstant(R.drawable.im18,"im18.jpg",32.58f,8.66f,196f,229f,322.2f,243.2f,-1.78f,0.36496f));
		dataset.put(R.drawable.im20, new USGConstant(R.drawable.im20,"im20.jpg",13.6f,3.81f,173f,278f,227.3f,193.8f,-15.5f,0.20492f));
		dataset.put(R.drawable.im21, new USGConstant(R.drawable.im21,"im21.jpg",27.36f,7.91f,187f,252f,291f,242.3f,5.12f,0.3268f));
		dataset.put(R.drawable.im22, new USGConstant(R.drawable.im22,"im22.jpg",28.05f,8.05f,210f,223f,275.1f,216.6f,1.8f,0.36496f));
		dataset.put(R.drawable.im23, new USGConstant(R.drawable.im23,"im23.jpg",15.38f,4.23f,152f,271f,222f,175.7f,12.2f,0.2451f));
		dataset.put(R.drawable.im24, new USGConstant(R.drawable.im24,"im24.jpg",15.4f,4.18f,188f,233f,221.4f,178.9f,3.3f,0.2451f));
		dataset.put(R.drawable.im25, new USGConstant(R.drawable.im25,"im25.jpg",24.87f,6.76f,167f,254f,274.4f,211.9f,-11.3f,0.3268f));
		dataset.put(R.drawable.im26, new USGConstant(R.drawable.im26,"im26.jpg",30.25f,8.61f,166f,272f,286.9f,242.6f,22.5f,0.36496f));
		dataset.put(R.drawable.im27, new USGConstant(R.drawable.im27,"im27.jpg",13.6f,3.83f,174f,276f,228.3f,192.2f,-15.5f,0.20492f));
		dataset.put(R.drawable.im28, new USGConstant(R.drawable.im28,"im28.jpg",26f,7.32f,195f,220f,277.7f,227.4f,-35.18f,0.3268f));
		dataset.put(R.drawable.im29, new USGConstant(R.drawable.im29,"im29.jpg",30.56f,8.85f,186f,224f,288.8f,246.3f,-4.37f,0.36496f));
		dataset.put(R.drawable.im30, new USGConstant(R.drawable.im30,"im30.jpg",24.97f,6.77f,171f,272f,315.9f,242f,7.8f,0.28571f));
		dataset.put(R.drawable.im31, new USGConstant(R.drawable.im31,"im31.jpg",21.11f,6.03f,181f,328f,260.8f,212.8f,-6.83f,0.28571f));
		dataset.put(R.drawable.im33, new USGConstant(R.drawable.im33,"im33.jpg",33.91f,9.53f,167f,260f,328.25f,262.3f,5.42f,0.36496f));
		dataset.put(R.drawable.im34, new USGConstant(R.drawable.im34,"im34.jpg",33.48f,10.05f,190f,251f,306.6f,279f,37.58f,0.36496f));
		dataset.put(R.drawable.im35, new USGConstant(R.drawable.im35,"im35.jpg",13.8f,3.94f,170f,298f,228.7f,199.2f,-7f,0.20492f));
		dataset.put(R.drawable.im37, new USGConstant(R.drawable.im37,"im37.jpg",23.21f,6.39f,192f,267f,284.5f,234.4f,-14.6f,0.28571f));
		dataset.put(R.drawable.im38, new USGConstant(R.drawable.im38,"im38.jpg",33.87f,9.8f,198f,256f,320.7f,270.5f,-10.7f,0.36496f));
		dataset.put(R.drawable.im40, new USGConstant(R.drawable.im40,"im40.jpg",31.25f,8.71f,198f,226f,297.3f,245f,-22.34f,0.36496f));
		dataset.put(R.drawable.im41, new USGConstant(R.drawable.im41,"im41.jpg",22.49f,6.58f,186f,273f,271.4f,234.1f,-7.62f,0.28571f));
		dataset.put(R.drawable.im42, new USGConstant(R.drawable.im42,"im42.jpg",30.83f,9f,187f,245f,291.9f,248.9f,12.46f,0.36496f));
		dataset.put(R.drawable.im43, new USGConstant(R.drawable.im43,"im43.jpg",12.66f,3.46f,172f,348f,218.1f,175f,-2.1f,0.20492f));
		dataset.put(R.drawable.im44, new USGConstant(R.drawable.im44,"im44.jpg",23.42f,6.5f,193f,270f,286.1f,234.1f,-1.4f,0.28571f));
		dataset.put(R.drawable.im45, new USGConstant(R.drawable.im45,"im45.jpg",32.72f,8.93f,209f,226f,320.2f,248f,14.47f,0.36496f));
		dataset.put(R.drawable.im46, new USGConstant(R.drawable.im46,"im46.jpg",30.32f,8.56f,202f,239f,286.9f,238.5f,4.6f,0.36496f));
		dataset.put(R.drawable.im47, new USGConstant(R.drawable.im47,"im47.jpg",26.77f,7.88f,184f,281f,326.1f,273.9f,19.71f,0.28571f));
		dataset.put(R.drawable.im48, new USGConstant(R.drawable.im48,"im48.jpg",29.54f,8.71f,179f,231f,271.3f,242.6f,-18.03f,0.36496f));
		dataset.put(R.drawable.im49, new USGConstant(R.drawable.im49,"im49.jpg",26.77f,7.51f,184f,260f,294f,232.5f,-21.77f,0.3268f));
		dataset.put(R.drawable.im50, new USGConstant(R.drawable.im50,"im50.jpg",25.32f,7.34f,180f,296f,302f,264.4f,-18.13f,0.28571f));
		dataset.put(R.drawable.im52, new USGConstant(R.drawable.im52,"im52.jpg",33.37f,9.59f,188f,203f,312.7f,270.4f,-15f,0.36496f));
		dataset.put(R.drawable.im54, new USGConstant(R.drawable.im54,"im54.jpg",32.68f,9.68f,178f,216f,307f,268.5f,-24.36f,0.36496f));
		dataset.put(R.drawable.im55, new USGConstant(R.drawable.im55,"im55.jpg",14.56f,4.23f,159f,252f,248.1f,207f,-11.63f,0.20492f));
		dataset.put(R.drawable.im56, new USGConstant(R.drawable.im56,"im56.jpg",30.38f,8.54f,169f,190f,285f,242.1f,0.8f,0.36496f));
		dataset.put(R.drawable.im58, new USGConstant(R.drawable.im58,"im58.jpg",28.65f,8.01f,184f,205f,307f,250.8f,12.23f,0.3268f));
		dataset.put(R.drawable.im59, new USGConstant(R.drawable.im59,"im59.jpg",26.3f,7.49f,194f,245f,316.2f,266.9f,1.81f,0.28571f));
	}
	
	public static USGConstant get(int imageId){
		if(!inited){
			init();
		}
		return dataset.get(imageId);
	}
}
