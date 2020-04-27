<template>
<div class="max-width-wrapper">
    

    <TopAdSpan />
    <div style="clear:both"></div>

    <div class='g-box-wrapper'>
	    <div class='r-content-box' v-if="!isRegistSuc">
            <!-- 账户信息 begin -->
            <div class="register-title">                
                    <span class="r-title">账号信息</span>
                    <span class="grey-text">(必填项)</span>
            </div>
            <!-- <div class="r-row first">   
                <div class="r-label">
                    <span class="req">*</span>用户名:
                </div>             
                <input type="text" class="input-cls" 
                    v-model="account"
                    placeholder="请输入您的用户名"> 4-20位字符，支持汉字、字母、数字及“-”、“_”组合。
            </div> -->
            <div class="r-row">   
                <div class="r-label">
                    <span class="req">*</span>用户名:
                </div>             
                <input type="text" class="input-cls" 
                    v-model="account"
                placeholder="请输入用户名"> 用户名是独一无二的用户标识
            </div>
            <div class="r-row">   
                <div class="r-label">
                    <span class="req">*</span>手机号:
                </div>             
                <input type="text" class="input-cls" 
                     @input="handleInput($event)" v-model="phone"
                placeholder="请输入手机号码"> 请填写正确的手机号码。
            </div>
            <div class="r-row">   
                <div class="r-label">
                    <span class="req">*</span>验证码:
                </div>             
                <input type="text" class="input-cls short" v-model="vcode">

                <GetIdentifyCode :phone="phone" :url="getVCodeUrl">
                    <!-- 获取验证码按钮 -->
                    <template slot-scope="props" slot="button">
                        <span class="v-btn" :class="{invalid:props.invalid}" v-html="props.identifyCodeTxt"></span>
                    </template>
                    <!-- 错误提示信息 -->
                    <template slot-scope="props" slot="msg">
                        <span class="error-tips">{{props.getIdentifyCodeMsg}}</span>
                    </template>
                </GetIdentifyCode>

                <!-- 如无法接收验证码... -->
            </div>
            <div class="r-row">   
                <div class="r-label">
                    <span class="req">*</span>设置密码:
                </div>             
                <input type="password" 
                    v-model="password"
                class="input-cls long-left"> 请输入6-20位大小写英文字母、符号或数字。
            </div>
            <div class="r-row">   
                <div class="r-label">
                    <span class="req">*</span>确认密码:
                </div>             
                <input type="password" 
                    v-model="rePassword"
                class="input-cls long-left"> 请再次输入相同的密码。
            </div>
            
            <div class='clear-both'></div>
            
            <!-- 账户信息 end -->
            
            <div class="r-row"> 
                        
                <span class="filter-item">
                    <input type='checkbox' id='read-statement' v-model="check">
                    <label for='read-statement'></label>我已阅读并同意<a @click="showAgreementLayout()">《小肥花商城用户协议》</a>
                </span>
            </div>                
            <div class="r-row center"> 
                        
                <span class="r-submit"
                    @click="doRegistration"
                    >
                    注册
                </span>                        
                <span class="error-text"
                    v-bind:class="errorMsg == '' ? 'hide': ''"
                    >
                    {{errorMsg}}
                </span>
            </div>
            
            


        </div>
        <div class="regist-suc" v-if="isRegistSuc">
            <p class="main-text">注册成功！</p>
            <p class="welcome">欢迎加入小肥花商城！</p>
            <div>
                <a class="btn1" href="/views/home/index.html">去商城购物</a>
            </div>
        </div>
    </div>
    
    <LoadingScreen
        ref='loadingScreen'/>

    <BottomArticle />
    <div style="clear:both"></div>

    <BottomLink />
    <div style="clear:both"></div>

</div>
</template>

<script>

    import Vue from 'vue'
    import TopAdSpan from '@/components/biz/TopAdSpan';
    import BottomArticle from '@/components/biz/BottomArticle';
    import BottomLink from '@/components/biz/BottomLink';
    import GetIdentifyCode from '@/components/biz/GetIdentifyCode';
    import LoadingScreen from 'components/com/LoadingScreen';
    import * as Ajax from '@/helper/Ajax';
    import * as Urls from '@/helper/Urls';
    

export default {
  data() {
    return {
        
        account: '',    // 待注册账号
        phone: '',      // 手机号码
        vcode: '',      // 发到手机的验证码
        password: '',               // 密码
        rePassword: '',             // 确认密码
        check: true,               // 是否阅读条款
        errorMsg: '',                      // 出错提示
        isRegistSuc:false,      //是否注册成功

        getVCodeUrl: Urls.user_getVCode4Register, 
        
    }
  },
  components: {
        TopAdSpan , BottomArticle, GetIdentifyCode
        , BottomLink, LoadingScreen
      
  },
  //实例初始化最之前，无法获取到data里的数据
  beforeCreate(){
  	
  	
  },  
  //在挂载开始之前被调用
  beforeMount(){
      
      
  
  }, 
  //已成功挂载，相当ready()
  mounted(){
      
  },
  updated(){
      
  },
  watch:{
  },
  //相关操作事件
  methods: {
            
        setErrorMsg: function(msg){
            this.errorMsg = msg;
        }, 
        Encrypt(word){  
            var key = CryptoJS.enc.Utf8.parse("abcdefgabcdefg12");   
            var srcs = CryptoJS.enc.Utf8.parse(word);  
            var encrypted = CryptoJS.AES.encrypt(word, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});  
            return encrypted.toString();  
        },
        // 进行注册
        doRegistration: function(){

            if(!this.check){
                return false;
            }
            let inst = this;
            let password,rePassword;
            
            if(!inst.account){
                inst.errorMsg = '请输入用户名';
                return;
            }else if(inst.account.length < 4 || inst.account.length > 20){
                inst.errorMsg = '用户名必须是4-20位字符';
                return;
            }

            if(!inst.phone){
                inst.errorMsg = '请输入手机号';
                return;
            }

            if(!inst.vcode){
                inst.errorMsg = '请输入验证码';
                return;
            }

            if(!inst.password){
                inst.errorMsg = '请输入密码';
                return;
            }else if(inst.password.length < 6 || inst.password.length > 20){
                inst.errorMsg = '密码必须是6-20位大小写英文字母、符号或数字';
                return;
            }

            if(!inst.rePassword){
                inst.errorMsg = '请再次输入密码';
                return;
            }else if(inst.password != inst.rePassword){
                inst.errorMsg = '两次输入密码不一致';
                return;
            }
            inst.$refs.loadingScreen.show();

            let url = Urls.user_register;
            let params = {
                account: inst.account, 
                phone: inst.phone, 
                vcode: inst.vcode, 
                password: inst.password, 
                rePassword: inst.rePassword, 
            };

            Ajax.post(url, params, function (server_data){
                inst.$refs.loadingScreen.hide();
                if(server_data.error){
                    inst.errorMsg = server_data.error;
                    inst.password = '';
                    inst.rePassword = '';
                    inst.vcode = '';
                    return;
                }
                inst.errorMsg = '';
                inst.isRegistSuc = true;
                console.log('--------- 注册成功');
                document.documentElement.scrollTop = 0;
            });
        
        },
        
        showAgreementLayout(){
            this.$refs.DModel.show();
        },
        closeAgreementLayout(){
            this.$refs.DModel.hide();
            this.check = true;
        },
        closeDModel(){
            this.$refs.DModel.hide();
        },
        
        
        handleInput(e){
        this.phone=e.target.value.replace(/[^\d]/g,'');
        }
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../../assets/scss/themeDefault.scss';

    .max-width-wrapper{
        width: 100%;
        margin: 0;
    }


    /** 注册表格 **/
    .g-box-wrapper{
        width: 100%;    
        background: #fff;
        font-size: 14px;
        .r-content-box{
            @include g-page-content-row;      
            
            .sep-150{
                width: 100%;
                height: 150px;
            }
        }

        .grey-text{
            color: #999;
        }

        .r-label{
            .req{
                color: $g-main-color;
                margin-right: 5px;
            }
            position: absolute;
            margin-left: 70px;
            line-height: 48px;
            color: #333;
        }
    }

    .r-content-box{
        .recomend{
            border: 1px solid #dad8d8;
            margin-top: 40px;
            padding-bottom: 40px;
        }
        .register-title{        
            margin-top: 20px;
            height: 50px;
            line-height: 50px;
            background: rgb(248, 248, 248);
            border: 1px solid #dad8d8;
            padding-left: 20px;
            &.second{
                border: 0 none;
                margin-top: 0;
            }
            .r-title{
                font-size: 14px;
                margin-right: 10px;
                font-weight: bold;
            }
        }
    }

    .r-content-box .r-row{
        position: relative;
        margin-top: 20px;
        color: #999;
        .input-cls{
            padding: 17px 20px 17px 100px;
            width: 300px;
            margin-left: 50px;
            margin-right: 40px;
            outline: 1px solid #ddd;
            border: none;

            &:focus{
            outline: 1px solid #999;
                border: none;
            }
        }
        .input-cls.short{
            width: 145px;
            margin-right: 10px;
            vertical-align: middle;
        }
        .input-cls.long-left{
            padding-left: 120px;
            width: 280px;
        }

        .v-btn{
            background: $g-main-color;
            padding: 0 30px;
            color: #fff;
            text-align: center;
            margin-right: 40px;
            cursor: pointer;
            line-height: 49px;
            display: inline-block;
            vertical-align: middle;
            .span{
                color: #cea1a1;
                font-size: 14px;
                margin-left: 5px;
            }
            &.invalid{
                background-color: #ccc;
                cursor: not-allowed;
            }
        }
        .grey-btn{
            padding: 20px 30px ;
            border: 1px solid #eaeaea;
            text-align: center;
            margin-right: 40px;
            cursor: pointer;
        }

        .succ{
            font-size: 48px;
            color: green;
        }
        .welcome{
            font-size: 24px;
        }
    }
    .r-row.first{
        margin-top: 40px;
    }
    

    .r-label-2{
        margin-left: 50px;
        margin-right: 40px;
    }

    .r-select{
        padding: 8px 15px;;
        width: 150px;
        background: transparent;
        margin-right: 24px;
        border-color: #ddd;
        option{

            line-height:30px;
            
            height:30px;
            
            margin:5px auto;
            
        }
    }

    .filter-item{
        padding: 4px 4px 4px 5px;
        position: relative;
        margin-top: 20px;
        float: left;
        width: 100%;
        font-size: 14px;
        a{
            color: #1d2088;
            cursor: pointer;
        }
        input[type=checkbox]{
            display: none;
        }
        label {
            display: inline-block;
            width: 12px;
            height: 12px;
            position: relative;
            background: #fff;
            border: 1px solid #dad8d8;
            margin-right: 5px;

            .checked{
                border: 1px solid $g-main-color;
            }
        }
        input[type=checkbox]:checked + label:after {
            content: '';
            position: absolute;
            width: 5px;
            margin: 2px 1px 2px 2px;
            height: 4px;
            background: transparent;
            border: 2px solid $g-main-color;
            border-top: none;
            border-right: none;
        
            -webkit-transform: rotate(-45deg);
            -moz-transform: rotate(-45deg);
            -o-transform: rotate(-45deg);
            -ms-transform: rotate(-45deg);
            transform: rotate(-45deg);
        }                
    }
    /** 已选择的查询条件 **/
    .filter-item.checked{
        color: $g-main-color;
        label {
            border: 1px solid $g-main-color;
        }             
    }

    .r-submit{
        background: $g-main-color;
        padding: 0 100px;
        background: #c80000;
        color: #fff;
        text-align: center;
        margin-right: 40px;
        font-size: 18px;
        cursor: pointer;
        display: inline-block;
        line-height: 50px;
        height: 50px;
        margin-bottom: 40px;
        margin-top: 20px;
    }
    .r-row.center{
        text-align: center;
        clear: both;
    }

    .error-text{
        font-size: 15px;
        color: red;
        position: absolute;
        top: 36px;
    }
    .error-text.hide{
        display: none;
    }

    .error-tips{
        color: red;
    }

    .dm-content{
        font-size: 13px;
        padding: 20px 40px;
        line-height: 24px;
        button {
            background-color: $g-main-color;
            border: 0 none;
            padding: 0 14px;
            cursor: pointer;
            color: #fff;
            height: 38px;
            line-height: 38px;
            margin: 36px auto 0;
            display: block;
        }
        .title{
            font-weight: bold;
            margin: 12px 0 3px;
        }
    }
    .dm-title {
        line-height: 64px;
        border-bottom: solid 1px #ddd;
        font-weight: bold;
        padding: 0 20px;
        background-color: #f8f8f8;
        .close {
            font-size: 28px;
            top: -6px;
            cursor: pointer;
            float: right;
            position: relative;
        }
    }

    // 注册成功
    .regist-suc{
        width: $g-main-width;
        margin: 60px auto 40px;
        border: solid 1px #ddd;
        text-align: center;
        .main-text{
            color: $g-main-color;
            font-size: 36px;
            margin-top: 90px;
            margin-bottom: 35px;
        }
        .welcome{
            font-size: 18px;
        }
        div{
            margin-top: 144px;
            margin-bottom: 96px;
            a{
                width: 210px;
                font-size: 24px;
                line-height: 60px;
                display: inline-block;
            }
            .btn1{
                color: #333;
                border: solid 1px #ddd;
            }
            .btn2{
                color: #fff;
                background-color: $g-main-color;
                margin-left: 95px;
            }
        }
    }
   
   
</style>
