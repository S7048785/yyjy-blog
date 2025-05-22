create table article
(
    id          bigint auto_increment
        primary key,
    title       varchar(256)              null comment '标题',
    content     longtext                  null comment '文章内容',
    summary     varchar(1024)             null comment '文章摘要',
    category_id bigint                    null comment '所属分类id',
    thumbnail   varchar(256)              null comment '缩略图',
    is_top      char      default '0'     null comment '是否置顶（0否，1是）',
    status      char      default '0'     null comment '状态（0已发布，1草稿）',
    view_count  bigint    default 0       null comment '访问量/浏览量',
    create_time timestamp default (now()) null,
    update_time timestamp default (now()) null,
    del_flag    int       default 0       null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '文章表';

create index idx_id_status_del
    on article (id, status, del_flag);

INSERT INTO blog.article (id, title, content, summary, category_id, thumbnail, is_top, status, view_count, create_time, update_time, del_flag) VALUES (1, 'TypeScript快速入门', '配置`TypeScript`环境

```
npm install typescript -g
```

使用命令编译`.ts`文件

```
tsc 文件名.ts
```



## 类型声明

### 声明基本类型

```tsx
let a: string; // 声明变量a只能存储字符串
let b: number;		// 声明b可存储数值

a = \'123\';
b = 123;
```

### 声明对象类型

```tsx
let person1: { name: string, age?: number };

person1 = {
    name: "张三",
    age: 27,
};
```

#### 索引签名

允许定义对象具有任意数量的属性

```tsx
let person: {
    name: string,
    age?: number,
    [key: string]: any
}

person = {
    name: \'张三\',
    age: 27,
    gender: \'男\'
}
```

### 声明函数类型

```tsx
// 声明
let func: (a: number, b: string) => string;

func = (a, b) => {
    return a + b;
}

// 定义
function func2(a: string, b: string): void {
    return;
}
```

函数类型声明还可以使⽤：接⼝、⾃定义类型等⽅式

### 声明数组类型

```tsx
let arr1: string[];
let arr2: Array<string>;

arr1 = arr2 = [\'1\', \'2\'];
```

### tuple元组

只能存储**固定数量**的一种**数组类型**

```tsx
// 第一元素必须是number类型, 第二类型必须是string类型
let arr1: [number, string];
// 第二参数可选，若有则必须是string类型
let arr2: [number, string?];
// 第一元素必须是number类型, 后⾯的元素可以是任意数量的 string 类型
let arr3: [number, ...string[]];

arr1 = [123, \'123\'];
arr2 = [123];
arr3 = [123, \'3\', \'44\', \'555\'];
```

### enum枚举

```tsx
enum Direction {
     Up, Down, Left, Right
}
console.log(Direction) // 打印Direction会看到如下内容
/* 
 {
 0:\'Up\', 
 1:\'Down\', 
 2:\'Left\', 
 3:\'Right\', 
 Up:0, 
 Down:1, 
 Left:2,
 Right:3
 } 
*/
// 反向映射
console.log(Direction.Up)
console.log(Direction[0])
```

### type自定义类型

type可以为任意类型创建别名,包括number,object等

```tsx
// 基本用法
type t1 = number;
type t2 = { name: string, age: number };
// 联合类型
type t3 = number | string;
type t4 = \'男\' | \'女\';
```

交叉类型

```tsx
//⾯积
type Area = {
 height: number; //⾼
 width: number; //宽
};
//地址
type Address = {
 num: number; //楼号
 cell: number; //单元号
 room: string; //房间号
};
// 定义类型House，且House是Area和Address组成的交叉类型
type House = Area & Address;
const house: House = {
 height: 180,
 width: 75,
 num: 6,
 cell: 3,
 room: \'702\'
};
```

引入外部的type类型

```tsx
import { type Person } from "../type/index.ts";
let person1: Person = {
    name: string,
    age: number
}
```



### interface

定义对象的格式，规定一个类需要实现哪些属性和方法

interface和type都可以定义对象结构。
但interface支持继承、合并。
type可以定义类型别名、联合类型、交叉类型



## 函数返回类型为void时的特殊情况

函数定义时，限制返回类型为`void`。那么返回值必须为空

```tsx
function demo():void {
    return;
}
```

使用类型声明限制函数返回值为`void`时，TS不会严格要求函数返回空

```tsx
type Func = () => void;

let f1: Func = () => {
    return 1; // 允许返回非空值
}

let demo1: () => void;
demo1 = () => {
  return 1;	// 允许返回非空值
}
```

', '简单介绍TypeScript的使用', 1, 'http://47.111.151.165:9000/blog/2025/05/13/typescript-logo.jpg', '1', '0', 16, '2025-05-07 19:21:03', '2025-05-07 19:21:06', 0);
INSERT INTO blog.article (id, title, content, summary, category_id, thumbnail, is_top, status, view_count, create_time, update_time, del_flag) VALUES (2, '文章标题', '文章内容', '文章摘要', 5, 'http://47.111.151.165:9000/blog/2025/05/13/83257e37-5244-4ec8-930a-3cfe9349f0f4.jpg', '0', '0', 5, '2025-05-07 17:59:06', '2025-05-07 17:59:06', 0);
INSERT INTO blog.article (id, title, content, summary, category_id, thumbnail, is_top, status, view_count, create_time, update_time, del_flag) VALUES (3, '日期格式化', '`@JsonFormat`和`@DateTimeFormat`的使用情景：

@JsontFormat：

+ RESTful API：通过RESTful API接收或发送日期时间字段的JSON数据时
+ JSON序列号/反序列化：需要将 Java 对象转换为 JSON 字符串或将 JSON 字符串转换为 Java 对象时。

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
```



@DateTimeFormat：

+ MVC控制器：需要接收来自HTTP请求的日期时间数据时

```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
```

', '`@JsonFormat`和`@DateTimeFormat`的使用情景', 1, null, '0', '0', 5, '2025-05-16 00:41:11', '2025-05-16 00:41:13', 0);
INSERT INTO blog.article (id, title, content, summary, category_id, thumbnail, is_top, status, view_count, create_time, update_time, del_flag) VALUES (4, 'Knife4j简单使用', 'Knife4j是为JavaMVC框架集成Swagger生成Api文档的增加解决方案

### 基于SpringBoot2

**引入依赖**

```xml
<!--Swagger-UI API文档生产工具-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        <!--swagger增强工具依赖包，方便生成接口文档。非必须导入-->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-spring-boot-starter</artifactId>
            <version>2.0.8</version>
        </dependency>
```

MVC配置类

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		/** 配置knife4j 显示文档 */
		registry.addResourceHandler("doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		/**
		 * 配置swagger-ui显示文档
		 */
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		/** 公共部分内容 */
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
```

配置knife4j或者swaggerui基本配置信息	(如果是微服务项目，需要在当前模块下resources/spring.factories中引入该类的全路径)

```java
@EnableSwagger2
@EnableKnife4j
@Configuration
public class Knife4jOrSwagger2Config {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("测试分组")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Knife4j or swagger-bootstrap-ui API V1.0")
                .description("swagger-bootstrap-ui-demo RESTful APIs")
                .version("1.0")
                .build();
    }
}
```

### 基于SpringBoot3

基于SpringBoot3进行配置，有以下注意点：

- SpringBoot3 只支持OpenAPI3规范
- Knife4j提供的starter已经引用springdoc-openapi的jar，开发者需注意避免jar包冲突
- JDK版本必须 >= 17

```xml
<!-- SpringBoot3 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-openapi3-jakarta-spring-boot-starter</artifactId>
    <version>4.5.0</version>
</dependency>
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-api</artifactId>
    <version>2.3.0</version>
</dependency>
```

WebMvcConfig类（MVC配置类）
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Autowired
  private LoginInterceptor loginInterceptor;
  /**
	 * 注册拦截器
	 * @param registry
	 */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
      .addPathPatterns("/login/**")
      .excludePathPatterns(
      "swagger-ui/**",
      "/swagger-resources/**",
      "/v3/**",
      "/webjars/**",
      "/doc.html"
    );
  }

  /**
	 * 设置静态资源映射
	 * @param registry
	 */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/doc.html", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/v3/**")
      .addResourceLocations("classpath:/META-INF/resources/")
      .setCachePeriod(0);
  }

  // 如果上面的配置仍然doc.html空白404，就用下面这个
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/systemPictures/**")
      .addResourceLocations("file:" + System.getProperty("user.dir")+ File.separator+"uploadFile"+File.separator+"systemPictures"+File.separator);
    registry.addResourceHandler("/uploadFile/pluginFiles/logo/**")
      .addResourceLocations("file:" + System.getProperty("user.dir")+ File.separator+"uploadFile"+File.separator+"pluginFiles"+File.separator+"logo"+File.separator);

    registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
```

如果有拦截器就需要排除swagger的路径；假如doc.html网页空白，将拦截器注释掉再运行即可，后面可以取消注释

 **全局异常处理器需加上@Hidden注解**

**注解说明**

1. @Tag    作用于Controller
   ```java
   @Tag(name = "名称", description="描述")
   ```

2. @Operation    作用于请求方法
   ```java
   @Operation(summary = "根据ID查询用户信息")
   ```

3. @Schema    作用于实体类的属性
   ```java
   @Schema(name = "用户id", type = "Long", required="")
   ```

4. @ParameterObject    作用于请求参数
   用于解析 实体类的请求参数

   ```java
   PageResult<CardMessageVO> getCardMessagePage(@ParameterObject CardPageQueryDTO cardPageQueryDTO);
   ```

   如果觉得每次都要添加该注解很麻烦，可以使用springdoc-openapi并添加如下配置
   ```xml
   <dependency>
       <groupId>org.springdoc</groupId>
       <artifactId>springdoc-openapi-starter-webmvc-api</artifactId>
       <version>2.3.0</version>
   </dependency>
   ```

   ```yaml
   springdoc:
     # 默认是false，需要设置为true
     default-flat-param-object: true
   ```', 'Knife4j是为JavaMVC框架集成Swagger生成Api文档的增加解决方案', 1, null, '0', '0', 7, '2025-05-16 00:43:44', '2025-05-16 00:43:45', 0);
INSERT INTO blog.article (id, title, content, summary, category_id, thumbnail, is_top, status, view_count, create_time, update_time, del_flag) VALUES (5, 'EsayExcel', 'ExcelDataConvertException数据转换异常
## 引入依赖
建议在model模块pom.xml内引入

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>3.1.0</version>
</dependency>
```

## 快速入门
1. 定义实体类，用于对应excel里面每一行的数据

```java
@Data
public class CategoryExcelVo {
	// 第一列数据
	@ExcelProperty(value = "id" ,index = 0)
	private Long id;
	// 第二列数据
	@ExcelProperty(value = "名称" ,index = 1)
	private String name;
	// 第三列数据
	@ExcelProperty(value = "图片url" ,index = 2)
	private String imageUrl ;
}
```

2. 定义监听器，用于**监听并存储**解析到的数据

```java
public class ExcelListener<T> extends AnalysisEventListener<T> {
	//可以通过实例获取该值
	private List<T> datas = new ArrayList<>();
	/**
	 * 监听到每一条数据都会执行此方法
	 */
	@Override
	public void invoke(T o, AnalysisContext analysisContext) {
		//数据存储到list，供批量处理，或后续自己业务逻辑处理。
		datas.add(o);
	}

	public List<T> getDatas() {
		return datas;
	}

	/**
	 * 解析完成之后执行此方法
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {

	}
}
```



## 读取数据

```java
public List<CategoryExcelVO> readData() {
  // 指定文件路径
		String filePath = "F:\\\\resource\\\\excel\\\\category.xlsx";
		// 创建监听器对象
		ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>();
		// 执行excel读取操作
		EasyExcel.read(filePath, CategoryExcelVo.class, excelListener)
				// 读取第0个sheet
				.sheet()
				// 执行读取操作 (同步读取)
				.doRead();
		//获取解析到的数据
		List<CategoryExcelVo> excelVoList = excelListener.getDatas();
		return excelVoList;
}
```



##   Util

```java
public class EasyExcelUtil {

  // 读取文件中所有数据 (第一张表)
	public static <T> List<T> read(String filePath, Class<T> clazz) {
		// 创建监听器对象
		ExcelListener<T> excelListener = new ExcelListener<>();
		// 执行excel读取操作
		try {
			EasyExcel.read(filePath, clazz, excelListener)
					// 读取第0个sheet
					.sheet()
					// 执行读取操作 (同步读取)
					.doRead();
		} catch (ExcelDataConvertException e) {
			throw new RuntimeException("文件读取失败");
		}
		//获取解析到的数据
		return excelListener.getDatas();
	}

  // 写入数据 (删除所有表, 写入第一张表内)
	public static <T> void write(String filePath, List<T> list) {
		// 执行excel写入操作
		EasyExcel.write(filePath, CategoryExcelVo.class)
				// 读取第0个sheet
				.sheet()
				// 执行读取操作 (同步读取)
				.doWrite(list);
	}
}
```



## 读取数据库并下载到浏览器

### Controller

```java
@GetMapping(value = "/exportData")
public void exportData(HttpServletResponse response) {
  categoryService.exportData(response);
}
```

### Service

```java
// ServiceImpl
public void exportData(HttpServletResponse response) {
  // 设置响应结果类型
  response.setContentType("application/vnd.ms-excel");
  response.setCharacterEncoding("utf-8");
  
  // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
  String fileName = URLEncoder.encode("文件名", "UTF-8");
  response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
  
  // 查询数据库中的数据，再转成VO对象
  List<DataVO> dataList;// ......
  
  // 写入数据到浏览器端
  EasyExcel.write(response.getOutputStream(), DataVo.class).sheet("sheet1").doWrite(dataList);
}
```

## 上传文件并存入数据库

### Controller

```java
@PostMapping("importData")
public Result importData(MultipartFile file) {
  categoryService.importData(file);
  return Result.build(null , ResultCodeEnum.SUCCESS) ;
}
```

### Service

```java
@Override
public void importData(MultipartFile file) {
  try {
    CategoryExcelListener<CategoryExcelVo> excelListener = new CategoryExcelListener<>(categoryMapper);
    // 调用read方法读取excel数据
    EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelListener).sheet().doRead();
  } catch (IOException e) {
    throw new GuiguException(500, "数据导入失败");
  }
}
```

### ExcelListener

ExcelListener不能交给Spring管理，要使用mapper就只能使用构造函数

```java
public class CategoryExcelListener<T> extends AnalysisEventListener<T> {

  /**
	 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
	 */
  private static final int BATCH_COUNT = 100;
  /**
	 * 缓存的数据
	 */
  private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

  //获取mapper对象
  private CategoryMapper categoryMapper;
  public CategoryExcelListener(CategoryMapper categoryMapper) {
    this.categoryMapper = categoryMapper;
  }

  // 每解析一行数据就会调用一次该方法
  @Override
  public void invoke(T o, AnalysisContext analysisContext) {
    cachedDataList.add(o);
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (cachedDataList.size() >= BATCH_COUNT) {
      saveData();
      // 存储完成清理 list
      cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    // excel解析完毕以后需要执行的代码
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库
    saveData();
  }

  private void saveData() {
    // 转成VO对象
    List<Category> map = CollUtil.map(cachedDataList, item -> BeanUtil.copyProperties(item, Category.class), true);
    categoryMapper.insert(map);
  }
}
```



## 前端下载文件 (数据导出)

```js
// ExportCategoryData是请求后端接口的方法
import { ExportCategoryData } from \'@/api/category.js\'

const exportData = () => {
  // 调用 ExportCategoryData() 方法获取导出数据
  ExportCategoryData().then(res => {
    // 创建 Blob 对象，用于包含二进制数据
    const blob = new Blob([res]);
    // 创建 a 标签元素，并将 Blob 对象转换成 URL
    const link = document.createElement(\'a\');
    link.href = window.URL.createObjectURL(blob);
    // 设置下载文件的名称
    link.download = \'分类数据.xlsx\';
    // 模拟点击下载链接
    link.click();
  })
}
```', 'ExcelDataConvertException数据转换异常', 1, null, '0', '0', 6, '2025-05-16 00:45:53', '2025-05-16 00:45:55', 0);
INSERT INTO blog.article (id, title, content, summary, category_id, thumbnail, is_top, status, view_count, create_time, update_time, del_flag) VALUES (6, 'CentOS7配置代理', '1. 编辑配置文件

   ```shell
   sudo vi /etc/profile
   ```

2. 添加代理配置
   在文件末尾添加以下内容，替换为自己的代理服务器信息：

   ```shell
   export http_proxy="http://username:password@yourproxy:port"
   export https_proxy="http://username:password@yourproxy:port"
   export ftp_proxy="http://username:password@yourproxy:port"
   export no_proxy="localhost, 127.0.0.1, ::1"
   ```

   示例：

   ```shell
   export http_proxy="http://root:123456@192.168.18.1:7897"
   export https_proxy="http://root:123456@192.168.18.1:7897"
   export ftp_proxy="http://root:123456@192.168.18.1:7897"
   export no_proxy="localhost, 127.0.0.1, ::1"
   ```

3. 保存文件并退出编辑器，然后执行以下命令使配置生效
   ```shell
   source /etc/profile
   ```

4. Clash Verge开启TUN模式和局域网连接', null, 1, null, '0', '0', 0, '2025-05-19 00:33:01', '2025-05-19 00:33:01', 0);
