# Alien_Library
###1,关于ListView，RecyclerView,ViewPager&Fragment的封装
ListView:
使用时Adapter继承ListAdapter,自定义ViewHolder继承ViewHolder类  
在自定义的adapter中实现initView(ViewHolder holder,T object)方法  
通过holder.getViewById(int id)来实例化item中的view，同时可以绑定数据  

adapter可以通过add()等方法动态添加数据。  

RecyclerView:  
自定义的Adapter继承RecyclerAdapter  
自定义的ViewHolder继承BaseViewHolder  
在自定义的ViewHolder中去实例化item中的view，通过重写setData()方法来绑定数据  

ViewPager$&Fragment  
在使用ViewPager&Fragment的时候，自定义的Adapter继承BaseFragmentPagerAdapter  
通过add()等方法来添加Fragment   

###2,网络请求库
if (BuildConfig.DEBUG) {  
            HttpRequest.setDebug(true, "NetWork");  
        }  
        AlienSet.init(this);  
图片加载压缩：http://blog.csdn.net/guolin_blog/article/details/9316683


CellView使用:
 Fresco.initialize(this);