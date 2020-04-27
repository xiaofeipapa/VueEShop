/**
 * 此类用于环境配置, 类似于Java的 config 文件. 所有和环境相关的变量都放到这个类. 
 * 部署的时候, 脚本会自动改变所有变量值. 
 */
var app_server = 'http://127.0.0.1:12000';

export {
    app_server
}