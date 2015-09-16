package com.echo.myflowlayoutdemo.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式的标签布�?
 * @author Administrator
 *
 */
public class FlowLayout extends ViewGroup{
	private int horizontalSpacing = 15;//水平间距
	private int verticalSpacing = 15;//行与行的垂直间距
	//用于存放�?��line对象
	private ArrayList<Line> lineList = new ArrayList<FlowLayout.Line>();
	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}
	
	/**
	 * 设置水平间距
	 * @param horizontalSpacing
	 */
	public void setHorizontalSpacing(int horizontalSpacing){
		this.horizontalSpacing = horizontalSpacing;
	}
	public int getVerticalSpacing() {
		return verticalSpacing;
	}
	/**
	 * 设置垂直间距
	 * @param verticalSpacing
	 */
	public void setVerticalSpacing(int verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}
	public int getHorizontalSpacing() {
		return horizontalSpacing;
	}

	/**
	 * 遍历子View，进行分行的操作,就是排座�?
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//清空lineLiST
		lineList.clear();
		
		//1.获取总宽�?其实包含paddingLeft和paddingRight
		int width = MeasureSpec.getSize(widthMeasureSpec);
		//2.得打除去padding的宽�?就是我们用于实际比较的�?宽度
		int noPaddingWidth = width - getPaddingLeft()-getPaddingRight();
		
		//3.遍历�?��子view，进行分�?
		Line line = null;
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);//获取当前子view
			childView.measure(0, 0);//通知父view测量childView
			
			if(line==null){
				line = new Line();//如果不换行，还是同一个line，如果换行就变了
			}
			//4.如果当前line�?��子view都木有，则直接放入line，不用比较，因为保证每行至少有一个子View
			if(line.getViewList().size()==0){
				line.addChild(childView);
			}else if(line.getWidth()+horizontalSpacing+childView.getMeasuredWidth()>noPaddingWidth) {
				//5.如果当前line的宽+水平间距+childView宽如果大于noPaddingWidth,�?��换行
				lineList.add(line);//先将之前的line存放起来
				
				line = new Line();//重新创建line，将childView放入到新行中
				line.addChild(childView);
			}else {
				//6.说明当前childView应该属于当前�?
				line.addChild(childView);
			}
			
			//7.如果当前childView是最后一个子View，会造成�?���?��line丢失
			if(i==(getChildCount()-1)){
				lineList.add(line);//将最后的�?��line存放起来
			}
		}
		
		//for循环结束，lineList中就存放了所有的line�?
		//计算layout�?��行的时�?�?��的高�?
		int height = getPaddingTop()+getPaddingBottom();//首先加上padding�?
		for (int i = 0; i < lineList.size(); i++) {
			height += lineList.get(i).getHeight();//再加上所有行的高�?
		}
		height += (lineList.size()-1)*verticalSpacing;//�?��加上�?��行的垂直间距
				
		//向父View申请�?��要的宽高
		setMeasuredDimension(width, height);
	}
	
	/**
	 * 对lineList中的line的所有子View进行布局，就是让每个人坐到自己的位置�?
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		
		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);//获取到当前的line对象
			
			//后面每一行的top值要相应的增�?当前行的top是上�?��的top�?height+垂直间距
			if(i>0){
				paddingTop += lineList.get(i-1).getHeight()+verticalSpacing;
			}
			
			ArrayList<View> viewList = line.getViewList();//获取line的子View集合
			
			//1.计算出留白的区域:noPaddingWidht-当前line的宽�?
			float remainSpace = getMeasuredWidth()-getPaddingLeft()-getPaddingRight()-line.getWidth();
			//2.计算出每个子View平均分到的宽�?
			float perSpace = remainSpace/viewList.size();
			
			for (int j = 0; j < viewList.size(); j++) {
				View childView = viewList.get(j);//获取当前的子View
				//3.将每个子View得到宽度增加到原来的宽度上面
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childView.getMeasuredWidth()+perSpace),MeasureSpec.EXACTLY);
				childView.measure(widthMeasureSpec, 0);//重新测量childView
				
				if(j==0){
					//每行的第�?��子View,�?��靠左边摆�?
					childView.layout(paddingLeft, paddingTop,paddingLeft+childView.getMeasuredWidth(),
							paddingTop+childView.getMeasuredHeight());
				}else {
					//摆放后面的子View，需要参考前�?��子View的right
					View preView = viewList.get(j-1);//获取前一个子View
					int left = preView.getRight()+horizontalSpacing;//前一个VIew的right+水平间距
					childView.layout(left, preView.getTop(),left+childView.getMeasuredWidth(),preView.getBottom());
				}
			}
		}
	}

	/**
	 * 封装每行的数据，包括每行的子View，行宽和行高
	 * @author Administrator
	 *
	 */
	class Line{
		private ArrayList<View> viewList;//用来存放当前行的�?��子TextView
		private int width;//当前行的�?��子View的宽+水平间距
		private int height;//当前行的行高
		public Line(){
			viewList = new ArrayList<View>();
		}
		
		/**
		 * 存放子view到viewList�?
		 * @param view
		 */
		public void addChild(View view){
			if(!viewList.contains(view)){
				
				//更新宽高
				if(viewList.size()==0){
					//说明没有子view，当前view是第�?��加入的，此时是不�?��加horizontalSpacing
					width = view.getMeasuredWidth();
				}else {
					//说明不是第一个，那么�?��加上水平间距
					width += horizontalSpacing + view.getMeasuredWidth();
				}
				//height应该是所有子view中高度最大的那个
				height = Math.max(view.getMeasuredHeight(), height);
				
				viewList.add(view);
			}
		}
		
		/**
		 * 获取子View的集�?
		 * @return
		 */
		public ArrayList<View> getViewList(){
			return viewList;
		}
		/**
		 * 获取当前行的宽度
		 * @return
		 */
		public int getWidth(){
			return width;
		}
		/**
		 * 获取行高
		 * @return
		 */
		public int getHeight(){
			return height;
		}
	}
}
