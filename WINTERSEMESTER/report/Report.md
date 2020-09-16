

# 智能终端软件开发技术期末作业

**Date : 2020-1-1**

> 课程名称：智能终端软件开发技术
>
> APP名称：扯淡聊天室
>
> 专业：信息安全
>
> 组长：刘非凡    STUDENT ID：3170102894
>
> 组员：章雨婷    STUDENT ID：3170102582
>
> 电子邮件地址：3170102894@zju.edu.cn / 3170102582@zju.edu.cn 
>
> 手机：17326085029 / 18888922655
>
> 任课老师：张寅





































# Contents #

* Outline
{:toc}


















## Chapter 1 : 系统简介





## Chapter 2 : 总体架构及功能划分

### Part 1.  总体架构

<img style=" zoom:60%; align:top" src="structure.png" />

### Part 2. 功能设计

#### Part 2.1  新建ITEM

新建ITEM调用右下角fab打开EDIT ITEM页面并写入文件名和存储路径名：

<img style=" zoom:60%; align:top" src="17.png" />

选择状态和优先级：

<img style=" zoom:60%; align:top" src="18.png" />

可以对TAG进行查询，还可添加TAG并显示：

<img style=" zoom:100%; align:top" src="19.png" />

时间戳进行滚动选择，可以通过按键进行日期的快速跳转：

<img style=" zoom:20%; align:top" src="32.png" />

可以通过下面按键实现加1天，加7天，减一天，减七天操作：

<img style=" zoom:100%; align:top" src="33.png" />



Notes写入的同时可以通过点击Notes按键对写入Notes的内容进行显示以方便检查：

<img style=" zoom:60%; align:top" src="20.png" />

通过修改路径名还可创建子文件，如图红色框出页面：

<img style=" zoom:60%; align:top" src="21.png" />

最后在files中检测文件是否成功存入，如图红色框出发现文件已经成功存入：

<img style=" zoom:60%; align:top" src="22.png" />

#### Part 2.2  时间轴文件清单格式

时间轴文件通过获取手机系统时间判断当前日期，并显示此日期前一天和后五天涵盖一周范围内的文件内容：

<img style=" zoom:20%; align:top" src="23.png" />

#### Part 2.3  TODO 文件清单格式

TODO文件从所有文件夹中提取TODO状态的文件，同时按照文件进行 分类，并统计每个文件中有多少个TODOtasks，每个item显示了文件的标题以及DEADLINE：

<img style=" zoom:20%; align:top" src="24.png" />

#### Part 2.4  全局文件清单格式

全局文件可以显示已创建的所有文件及其最后修改时间：

<img style=" zoom:20%; align:top" src="25.png" />

可对文件名进行搜索过滤：

<img style=" zoom:20%; align:top" src="26.png" />

点进文件会显示其中的所有item，如果想要查看二级item可以点击一级item左边的expand符号进行展示：

<img style=" zoom:60%; align:top" src="27.png" />

同时可以搜索item中包含的关键字内容并进行标红显示：

<img style=" zoom:20%; align:top" src="28.png" />

#### Part 2.5  搜索过滤功能

搜索过滤功能仅限于TAGS的搜索过滤以及文件中，文件夹名称搜索过滤和item内容搜索过滤

* TAGS搜索

  <img style=" zoom:60%; align:top" src="29.png" />

* 文件夹名称搜索过滤

  <img style=" zoom:60%; align:top" src="30.png" />

* item内容搜索过滤

  <img style=" zoom:60%; align:top" src="31.png" />

#### Part 2.6  多级子目录功能

多级子目录通过点进文件会显示其中的所有item，如果想要查看二级item可以点击一级item左边的expand符号进行展示：

<img style=" zoom:60%; align:top" src="27.png" />

#### Part 2.7  文件存储格式

为了方便手机查询org-mode文件，将文件名后缀定义为.txt方便手机查看文件，文件在手机上存储的具体格式如下：

<img style=" zoom:20%; align:top" src="16.png" />

## Chapter 3 : 关键数据结构/算法

### Part 3.1 Firebase数据库

Firebase是一个移动平台，可协助快速开发高品质的应用程序，同时Firebase也提供了庞大数据库供安卓开发使用。

首先纳入 google-services 插件和 Google 的 Maven 代码库：

```
buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.4.1'
        classpath 'com.google.gms:google-services:4.3.2'
    }
}
```

在应用的 Gradle 文件中添加Firebase SDK 的依赖项和apply plugin：

```
implementation 'com.google.firebase:firebase-core:17.2.1'
implementation 'com.google.firebase:firebase-auth:17.0.0'
implementation 'com.google.firebase:firebase-database:17.0.0'
implementation 'com.firebaseui:firebase-ui:0.6.2'
implementation 'com.firebaseui:firebase-ui-auth:4.0.0'
```







### Part 3.2 手机电量交互

该款APP主要设计理念在于获取手机电量并随机设置许可进入电量以形成随机控制人员进入的功能。

当手机电量发生改变时，系统会对外发送Intent的Action 为ACTION_BATTERY_CHANGED常量广播。当手机电量过低时,系统会发送Intent的Action为ACTION_BATTERY_LOW常量的广播。所以可以通过开发监听对应的Itent的BroadcastReceiver：

```java
intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
batteryStatus = registerReceiver(null, intentFilter);
battery = batteryStatus.getIntExtra("level", -1);
```

同时，根据手机电量变化也设计了相关电量颜色变化显示：

```java
private void setBatteryInToolbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                batteryInToolbar.setText(battery + "%");
                if(battery <= 100 && battery > 90) batteryInToolbar.setTextColor(Color.parseColor("#009688"));
                if(battery <= 90 && battery > 80) batteryInToolbar.setTextColor(Color.parseColor("#4CAF50"));
                if(battery <= 80 && battery > 70) batteryInToolbar.setTextColor(Color.parseColor("#8BC34A"));
                if(battery <= 70 && battery > 60) batteryInToolbar.setTextColor(Color.parseColor("#CDDC39"));
                if(battery <= 60 && battery > 50) batteryInToolbar.setTextColor(Color.parseColor("#FFEB3B"));
                if(battery <= 50 && battery > 40) batteryInToolbar.setTextColor(Color.parseColor("#FFC107"));
                if(battery <= 40 && battery > 30) batteryInToolbar.setTextColor(Color.parseColor("#FF9800"));
                if(battery <= 30 && battery > 20) batteryInToolbar.setTextColor(Color.parseColor("#FF5722"));
                if(battery <= 20 && battery > 10) batteryInToolbar.setTextColor(Color.parseColor("#FFFFFF"));
                if(battery == 10) batteryInToolbar.setTextColor(Color.parseColor("#FFFFFF"));
                if(battery == 9) batteryInToolbar.setTextColor(Color.parseColor("#EBEBEB"));
                if(battery == 8) batteryInToolbar.setTextColor(Color.parseColor("#D5D5D5"));
                if(battery == 7) batteryInToolbar.setTextColor(Color.parseColor("#BEBEBE"));
                if(battery == 6) batteryInToolbar.setTextColor(Color.parseColor("#A5A5A5"));
                if(battery == 5) batteryInToolbar.setTextColor(Color.parseColor("#8B8B8B"));
                if(battery == 4) batteryInToolbar.setTextColor(Color.parseColor("#727272"));
                if(battery == 3) batteryInToolbar.setTextColor(Color.parseColor("#535353"));
                if(battery == 2) batteryInToolbar.setTextColor(Color.parseColor("#383838"));
                if(battery == 1) batteryInToolbar.setTextColor(Color.parseColor("#202020"));
                if(battery == 0) batteryInToolbar.setTextColor(Color.parseColor("#000000"));
            }
        });
}
```



### Part 3.3 Hardware.Sensor
Android平台支持一些用于监视设备动作的传感器以便于更好的安卓开发设计，该APP游戏界面设计中主要使用了加速传感器（ TYPE_ACCELEROMETER）来计算手机加速度的变化以得到手机在空中飞行的时长：

```java
//Retrieve SensorManager, Android's tool for manipulating phone sensors
sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

//Set SensorManager to retrieve accelerometer data
accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//Register this Activity as a listener for changes in phone acceleration detected by the accelerometer
sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
```




## Chapter 4 : 开发困难及解决方案

### 4.1 困难1

* 困难

  

* 解决方案

  

### 4.2 困难2

* 困难

  

* 解决方案

  

### 4.3 困难3

* 困难

  

* 解决方案

  

### 4.4 困难4

* 困难

  

* 解决方案

  


## Chapter 5 : 小组分工

刘非凡：

章雨婷：




## Chapter 6 : 总结

该款APP小程序主要设计理念是概念性轻聊天软件，聊天室以手机电量为主要用户信息，通过邮箱注册登入聊天室，实现不同安卓终端设备之间的同步聊天和聊天记录更新。同时在界面设计上融入了电量颜色变化警示。在功能上实现了基本的用户登入登出，发送消息和同步接收，以及聊天室配备的小游戏。基本上身为一个聊天软件，功能齐全，富有新意和创意。

这款APP可以改进的功能还有很多，例如：

* 聊天室仅限于文字聊天和相关emoji表情包，由于Firebase上申请的数据库容量过小，难以实现语音和图片的传输，后续改进可以考虑扩充数据库的大小，增添语音和图片的传输功能
* 在推送提醒上，概念型的简易版聊天室缺少像QQ、微信等社交软件的消息推送功能，实用性不是特别强
* 聊天室小游戏设计方面，由于需要获取手机运行加速度，只有在手机回归源抛出起始点时才可计算抛出的时间，满足测试的手机轨迹必须为垂直自由落体，不是很人性化

总之，作为聊天APP总体基础功能基本上实现了，界面美观大方，UI交互非常边界流畅，加载速度较快，总体来说实现的APP功能比较完善，也学到了很多知识，收获颇丰。
