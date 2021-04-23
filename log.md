### 11月7日
**开始学习并开发博客项目，选型为一个基于springboot+vue开发的前后端分离博客项目**

`技术选型`:后端：SpringBoot整合Mybatis

`进度`:初步完成了框架的搭建，在pom中导入了现阶段所需的jar包。

`问题`:在进行数据库连接测试时无法成功连接(11月8日已解决)

`解决方案`:
*1.检查yml文件的格式是否正确*
*2.依赖问题，需导入junit依赖：junit-platform-launcher*

`开发时间`:约4小时

### 11月8日
**考虑到后面可能需要做集群、负载均衡等，所以就需要会话共享，而shiro的缓存和会话信息，一般考虑使用redis来存储这些数据，所以，不仅
仅需要整合shiro，同时也需要整合redis，而因为需要做的是前后端分离项目的骨架，所以一般会采用token或者jwt作为跨域身份验证解决方案。
所以整合shiro的过程中，需要引入jwt的身份验证过程**

**作为前后端分离项目，后端返回给前端时需要有一个统一的格式，因此封装统一结果集**

`新增技术选型`:后端：整合shiro+jwt会话共享

`进度`:完成了SpringBoot整合Mybatis，并成功连接数据库，完成封装统一结果集，学习了shiro框架的
部分内容。

`开发时间`:约2小时

### 11月9日
****

`进度`:学习完shiro基础。

### 11月10日
****

`进度`:学习完jwt基础。

**在今日学习完shiro和jwt基础后发现还有很多需要整合的地方，决定深入学习jwt，也就是jjwt(jwt框架)
，与spring boot集成shiro和redis**

### 11月12日-11月14日
****

**学习完了shiro和jwt的大部分知识，虽然还不是很熟练，但已经掌握了主要思想和实现步骤，决定先一步一步的
整合框架**

`过程`:

_1.首先配置ShiroConfig,使用shiro-redis-spring-boot-starter的jar包(具体使用方法详见github官方文档)
,在这一步需要注意的是需要自己引入ShiroFilterFactoryBean，在里面自定义JwtFilter过滤器进行判断请求头中是否
含有jwt信息，有就登录，没有就跳过，跳过后有Controller中的shiro注解进行再次拦截。_

_2.定义的JwtFilter需要继承AuthenticatingFilter，这是shiro内置的过滤器，可以执行自动登录方法，自己去定义
JwtToken类以便于封装jwt,在jwtFilter中重写两个方法，一个为返回jwt值，另一个为判断jwt值是否过期判断jwt值是否
过期需要写一个jwt的工具类。_

_3.在JwtFilter类中需要重写onLoginFailure方法，处理登录的异常，封装异常数据并抛出.在这一步骤，我们先得到抛出的
异常信息，用自定义的result类封装异常信息，并把其转换为json对象返回给前端(这里使用的JSON转换工具是阿里的fastjson包)_

_4.最后进行shiro登录逻辑的开发，在doGetAuthenticationInfo登录认证这个方法中，(String) jwtToken.getPrincipal()
拿到token的信息，再通过Claim中的getSubject方法得到userId，再调用service中的findById方法拿到用户信息进行判断，
又因为返回的信息SimpleAuthenticationInfo中有需要进行封装的信息，所以还需要自定义一个Profile用于封装用户非私密性的信息_

`思考`:
_步骤一思考：shiro的配置文件基本都大同小异，需要注意的是在整合shiro+redis的时候不仅需要参考官方文档的标准进行
，还需要自己思考是否需要引入其他对象，进行其他操作，既然已经有了官方的jar包整合shiro+redis，剩下的就需要取整合
jwt，自己应该明白需要在哪一步进行jwt信息的判断，并在其中进行设计牢记shiro三大对象Subject，SecurityManage
,Realm.ShiroConfig中的ShiroFilterFactoryBean在进行整合的时候被我忘了，这个工厂类很重要，有了它才能对角色权限进行设置
切记不能忘记_

_步骤二思考：shiro内置的AuthenticatingFilter过滤器可以先去了解它内置的方法再去写代码，仔细阅读封装的方法，可以发现
createToken得到的jwt可以封装进入Realm的authenticationToken中，在Realm中又需要去封装jwt，但默认方法是使用
UsernamePasswordToken封装封装用户的登录信息，这时候就需要自己去定义JwtToken类以便于封装jwt_

_步骤三思考：shiro内置的AuthenticatingFilter过滤器中executeLogin方法如果抛出异常需要自己重写这个异常_



`进度`:整合完成。

### 11月14日

**整合完成shiro+redis+jwt，发现在整合过程中会抛出很多异常，为了捕获这些异常，返回给前端统一的格式数据
需要进行全局异常处理**

`知识点复习`:@RestControllerAdvice:实现全局异常处理，只需要定义类，添加该注解即可定义方式

@ExceptionHandler(value = RuntimeException.class):用来指明异常的处理类型,这个异常的处理，是全局的，
所有类似的异常，都会跑到这个地方处理。

@ResponseStatus(HttpStatus.BAD_REQUEST):为了改变HTTP响应的状态码

`新知识`:@RequiresAuthentication:验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时。
需要进行登录才能访问.(shiro中的注解)

**实体校验：表单提交数据时，当json数据转换成实体时，如果实体中的字段规定了要求，就需要按要求进行，否者
就抛出异常。**

`注意`:Hibernate validatior在springboot的最新版本中未集成进来，需要自己手动导入坐标。

`新知识`:@RequestBody主要用来接收前端传递给后端的json字符串中的数据(请求体中的数据)；get方式无请求体
所以使用@RequestBody接收数据时，前端不能使用get方法提交数据，而是使用post方法。在后端的同一个接收方法里，
@RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，而@RequestParam()可以有多个。

@Validated：在controller层开启数据校验功能

此步骤可通过postman的post请求进行验证

**为了解决跨域问题，还需要另外引入一个解决跨域的配置文件。因为是前后端分离项目，还需要在拦截器中提供跨域支
持，这样才不会在进入controller之前就被wtFilter就拦截器拦截下来**

**接下来就是登录接口的开发。目前打算只开发一款小型的个人博客，就不用管理员登录了，功能有登录，注销**
**在进行登录判断时用到了断言，以及之前所学的知识整合**

`后期可改进`：
1.由于使用的是mybatis，是自己写的sql语句，还可以继续优化查询方法。select *不推荐使用。

2.登录中的md5加密和用户信息封装都采用了hutool工具类，后期可改进。md5加密可以自己写，用户信息封装也可以自己
写一个接口。

`难点`：在密码正确后需要生成jwt(需要先注入jwtUtils)，用generateToken()方法传用户的id参数进去。
把jwt放入hander中。

`进度`：完成了登录接口的开发

### 11月15日

**今天开始进行博客显示页面接口的开发。**

`步骤`：
**1.博客的页面中需要显示的数据有当前页面，页面的总数据，当前页面的内容等等，为了返回给前端这些数据，我选用
mybatis的plugins标签中的PageHelper插件(当然需要提前导入坐标)，sql语句还是自己写，但是要注意需要按
创建时间的降序返回数据库中的相关内容，然后使用PageHelper中的PageInfo方法获得与分页相关参数，通通返回给前端
这里需要注意的是需要把分页查询出来的数据pageData作为PageInfo的参数。**


`开发中遇到的问题`:
**1.在测试接口的时候，报错java.lang.StackOverflowError: null，并指定错误位置为com.adgerjay518.service.impl.BlogServiceImpl
，一看到报错信息就不难知道是栈溢出错误，可是我寻思我也没有在方法里面写循环？最后折腾了大半小时，改了很多配置
文件也没用后，还是把错误锁定到了是由于某些错误导致深度递归，抛出此错误以指示应用程序的堆栈已耗尽。最后在
自己的不懈努力下(2分钟),发现自己实现类注入的不是mapper，而是service。。。**