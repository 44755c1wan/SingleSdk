# gradle引入方法
第一步：
在项目根目录build.gradle文件中添加如下：
	 allprojects {
        repositories {
            ...
            maven {
                url "https://jitpack.io"
                credentials { username jp_hvgetm7g5r24fglrs3nr96rrtr }
            }
        }
     }
