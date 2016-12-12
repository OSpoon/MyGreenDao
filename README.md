buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.1'
    }
}

apply plugin: 'com.android.application'
apply plugin: 'org.greenrobot.greendao'

dependencies {
    compile 'org.greenrobot:greendao:3.2.0'
}


，greendao配置元素支持的配置选项主机：

schemaVersion：数据库架构的当前版本。这是使用的 * OpenHelpers类模式版本之间迁移。如果你改变你的实体/数据库架构，这个值必须增加。默认为1。
daoPackage：用于生成的DAO，DaoMaster和DaoSession包名称。 默认为源实体的包名。
targetGenDir：其中，生成源应保存在该位置。 默认为构建目录里面生成的源文件夹（ build/generated/source/greendao）。
generateTests：  设置为true，自动生成单元测试。
targetGenDirTests：  在哪里产生的单元测试应该被存储在基本目录。默认为 SRC / androidTest / java



一旦你的项目建成后，你就可以开始你的Android项目中使用greenDAO。

以下核心类是greenDAO的基本界面：

DaoMaster：入口点使用greenDAO。DaoMaster保存数据库对象（SQLiteDatabase）和管理DAO类（而不是对象）为特定的模式。它的静态方法来创建表或删除它们。它的内部类OpenHelper和DevOpenHelper是创建SQLite数据库架构SQLiteOpenHelper实现。

DaoSession：管理所有可用的DAO对象为特定的模式，您可以使用获得的getter方法之一。DaoSession还提供了一些通用的持久性的方法，如插入，装载，更新，刷新和删除实体。最后，DaoSession对象也跟踪标识范围。有关详细信息，看一看的会议文件。

的DAO：数据访问对象（DAO）坚持和查询实体。对于每一个实体，greenDAO生成一个DAO。它具有更持久的方法比DaoSession，例如：计数，LOADALL和insertInTx。

实体：持久化对象。通常情况下，实体是使用标准的Java性能（如POJO或一个JavaBean）表示数据库行对象。

核心初始化

最后，下面的代码示例说明了第一步初始化数据库和核心greenDAO类：


// do this once, for example in your Application class
helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
db = helper.getWritableDatabase();
daoMaster = new DaoMaster(db);
daoSession = daoMaster.newSession();
// do this in your activities/fragments to get hold of a DAO
noteDao = daoSession.getNoteDao();
// do this once, for example in your Application class
helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
db = helper.getWritableDatabase();
daoMaster = new DaoMaster(db);
daoSession = daoMaster.newSession();
// do this in your activities/fragments to get hold of a DAO
noteDao = daoSession.getNoteDao();
该示例假定一个 说明实体存在。凭借其DAO（ noteDao对象），我们可以调用持久性操作这个特定的实体。
