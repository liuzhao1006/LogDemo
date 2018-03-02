# LogDemo
    获取log日志存放到sdcard上,
## 主要工具类有LogUtils和LogFilesUtils.
#### 使用方式是

    第一步: 初始化日志类,参数可以自定义,例如:
          LogUtils.setAppLogDir(LogUtils.LOG_ROOT_PATHE + LogUtils.APP_LOG_PATHE,0+"",1,-1);
    第二步: 正常日志操作,并且在控制台能够看到.例如.
          LogUtils.i("刘朝","点击了按钮,同时改变了值");
    第三步: sdcard中查看日志,输出的日志如下:
          文件名为:2018-03-02-23-10-500.log
          2018-03-03 00:02:26.821 pid=12744 刘朝: 点击了按钮,同时改变了值
    
# 使用依赖

#### Gradle中
- Step 1. Add the JitPack repository to your build file

        allprojects {
        		repositories {
        			...
        			maven { url 'https://jitpack.io' }
        		}
        	}
- Step 2. Add the dependency

        dependencies {
    	        compile 'com.github.liuzhao1006:LogDemo:v1.0.0'
    	}
    	
    	