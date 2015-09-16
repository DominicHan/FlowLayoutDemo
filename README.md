# FlowLayoutDemo
流式标签布局

# 1.自定义FlowLayout,继承自ViewGroup; #

# 2.根据数据容器的size，往FlowLayout添加对应数量的子TextView; #

# 3.进行的具体的分行的逻辑: #
 a.定义Line类，封装每行的数据;
 b.在onMeasure方法中，遍历所有子view；
 c.如果当前line一个子view都木有，则直接将当前childView放入line中，不用再比较；
 d.如果当前line的width+水平间距+当前childView的宽大于noPaddingWidth,则需要换行，
   如果不大于，则将childView存放到line对象中;
 e.如果当前childVIew是最后一个子View，则需要将最后的line对象存入lineList中，否则会丢失;
 
# 4.为了能够摆放所有line的子View,需要向父View申请对应的width和height #

# 5.在onLayout方法中，进行具体的摆放子View的操作： #

# 6.留白区域的处理: #
a.计算出每行的留白的值；
b.计算出每个子View平均分配到的空间；
c.将每个子View得到的空间增加到原来的宽度上面，去填补留白区域；
