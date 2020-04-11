# AndroidStudio类库引入方法
第一步：
下载源码 ：git@github.com:dongyonghui/QtsdkSingle.git ，把类库引入项目

第二步：
在项目app 的build.gradle文件中添加依赖：
	dependencies {
        implementation project(path: ':sdklib')
    }


# gradle引入方法
第一步：
在项目根目录build.gradle文件中添加如下：
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
第二步：
在项目app 的build.gradle文件中添加依赖：
	dependencies {
        implementation 'com.github.dongyonghui:QtsdkSingle:2.1'
    }
# gradle引入方法