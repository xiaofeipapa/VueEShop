<template>
    <div>                
        <div class="login-box" v-if="isAccountLogin">               
            <div class="r-1">
                密码登录<span class="duan" @click="_changeLoginStyle">验证码登录 <i class="fa fa-angle-double-right"></i></span>
            </div>   
            <div class="box-content">
                <span class='error-t'>{{errorMsg}}</span>
                
                <div class="r-2">
                    <div class="float-img">
                        <img src='../../assets/image/small_user.png'>                                
                    </div>
                    <input type="text" class="input-cls" placeholder="用户名或手机号"
                        v-model="accountOrPhone"
                        @keyup.13="$refs.inp1.focus()"
                    >
                </div>  
                <div class="r-3">
                        <div class="float-img">
                            <img src='../../assets/image/pass.png'>                                
                        </div>
                    <input type="password" class="input-cls" placeholder="密码"
                        v-model="password"
                        @keyup.13=";if(isShowImageCode){$refs.inpImgCode.focus()}else{_loginByAccountPassword();}"
                        ref="inp1"
                    >
                </div>
                <div class="r-3" v-show="isShowImageCode">
                    <div class="check-content">
                        <span class="getmsg"><input type="text" v-model="imgCode" @keyup.13="_loginByAccountPassword" ref="inpImgCode" placeholder="请输入图形验证码"></span>
                        <!-- 获取验证码按钮 -->
                        <img class="btn" @click="_getImageCode" :src="imageCodeSrc" style="height:38px">
                    </div>
                </div>
                <div class="r-5">
                    <div class="login-btn"                            
                        @click="_loginByAccountPassword"
                    >登录</div>
                </div>
                <div class="r-4">
                    <a class="a1" href="/views/user/register.html">快速注册</a>
                </div>
                
                <!-- <div class="r-6">
                    <span class="line"></span><span class="ban">或通过以下账号登录</span>
                </div>
                <div class="r-7">
                    <img src='../../assets/image/qq.png' class="qq">
                    <img src='../../assets/image/wechat.png'>
                </div> -->
            </div>
                
        </div>
        <div class="login-box" v-if="!isAccountLogin">               
            <div class="r-1">
                验证码登录<span class="duan" @click="_changeLoginStyle">密码登录 <i class="fa fa-angle-double-right"></i></span>
            </div>   
            <div class="box-content">
                <span class='error-t'>{{errorMsg}}</span>
                
                <div class="r-2">
                    <div class="float-img">
                        <img src='../../assets/image/small_user.png'>
                    </div>
                    <input type="text" class="input-cls" placeholder="手机号" 
                        v-model="telephone"
                        @input=";if(telephone.charAt(0) == '0'){telephone = telephone.substring(1)}else{telephone = telephone.replace(/\D/g,'');}"
                        @keyup.13="$refs.inp2.focus()"
                    >
                </div>  
                <div class="r-3">
                    <div class="check-content">
                        <span class="getmsg"><img src='../../assets/image/pass.png'><input type="text" v-model="vcode" placeholder="短信验证码" ref="inp2" @keyup.13="_loginByPhone"></span>
                        <span class="btn no-allow" v-if="telephone.length != 11">获取验证码</span>
                        <template v-else>
                        <GetIdentifyCode :phone="telephone" :url="getVCodeUrl" 
                                @getIdentifyCodeMsg = "getIdentifyCodeMsg">
                            <!-- 获取验证码按钮 -->
                            <template slot-scope="props" slot="button">
                                <span class="btn" :class="{'invalid':props.invalid}" v-html="props.identifyCodeTxt"></span>
                            </template>
                            <!-- 错误提示信息 -->
                            <template slot-scope="props" slot="msg">
                                <!-- <span class="error-tips">{{props.getIdentifyCodeMsg}}</span> -->
                            </template>
                        </GetIdentifyCode>
                        </template>
                    </div>
                </div>
                <div class="r-5">
                    <div class="login-btn"                            
                        @click="_loginByPhone"
                    >登录</div>
                </div>
                <div class="r-4">
                    <a class="a1" href="/views/user/register.html">快速注册</a>
                </div>
                
                <!-- <div class="r-6">
                    <span class="line"></span><span class="ban">或通过以下账号登录</span>
                </div>
                <div class="r-7">
                    <img src='../../assets/image/qq.png' class="qq">
                    <img src='../../assets/image/wechat.png'>
                </div> -->
            </div>
                
        </div>
    </div>
                   

</template>

<script>

    import Vue from 'vue'
    import GetIdentifyCode from 'components/biz/GetIdentifyCode';
    import * as Urls from '@/helper/Urls';
    import * as Ajax from '@/helper/Ajax';
    import * as Events from '@/helper/Events';
    
    import { mapState, mapMutations, mapGetters } from 'vuex'
    import store from '@/store'
    

export default {

    store, 
    
  data() {
    return {
        
        isAccountLogin:true,
        accountOrPhone: '',    // 用户名或手机
        password: '',               // 密码        
        errorMsg: '',                      // 出错提示
        //短信登录
        telephone:"",
        vcode:"",
        isShowImageCode:false,      //是否显示图形验证码
        imageCodeSrc:"",            //图形验证码图片地址
        imgCode:"",                 //图形验证码

        getVCodeUrl: Urls.user_getVCode4Login, 
    }
  },
  components: {
        GetIdentifyCode      
  },
  watch:{
      
  },
  computed:{
            
      
  },
  //相关操作事件
  methods: {
      ...mapMutations('userStore', [
            'initUser'
        ]),
      
    _changeLoginStyle(){
        this.isAccountLogin = !this.isAccountLogin;
        this.accountOrPhone = "";
        this.password = "";
        this.errorMsg = "";
        this.telephone = "";
        this.vcode = "";
        
    },
    // 账号密码登录
	_loginByAccountPassword: function(){
        let inst = this;

        if(!inst.accountOrPhone){
            inst.errorMsg = '请输入手机号';
            return false;
        }else if(!inst.password){
            inst.errorMsg = '请输入密码';
            return false;
        }else if(inst.isShowImageCode && !inst.imgCode){
            inst.errorMsg = '请输入图形验证码';
            return false;
        }

        let url = Urls.user_loginByAccount;
        let params = {
            accountOrPhone: inst.accountOrPhone, 
            password: inst.password,      
            ic:inst.imgCode,      
        };

        Ajax.post(url, params, function (server_data){

            if(server_data.error){
                // 需要图形验证码
                if(server_data.errorCode == 'PASS_NO_IC'){
                    inst._getImageCode();
                    inst.isShowImageCode = true;
                    return;
                }
                
                // 其他情况显示错误即可
                inst.errorMsg = server_data.error;
                inst.password = '';
                inst.imgCode = '';
                return;
            }
            
            inst.errorMsg = '';
            inst._afterLogin(server_data.data);
        });
        
    }, 
    _getImageCode(){
        this.imageCodeSrc = Urls.user_getImageCode + "?" + Math.random();
    },
    //手机验证码登录
	_loginByPhone(){
        let inst = this;
        if(!inst.telephone){
            inst.errorMsg = '请输入手机号';
            return false;
        }else if(!inst.vcode){
            inst.errorMsg = '请输入验证码';
            return false;
        }else if(!/^1[0-9]{10}$/.test(inst.telephone)){
            inst.errorMsg = "请输入合法的手机号码";
            return false;
        }

        let url = Urls.user_loginByPhone;
        let params = {
            phone: inst.telephone, 
            vcode: inst.vcode,            
        };

        Ajax.post(url, params, function (server_data){

            if(server_data.error){
                inst.errorMsg = server_data.error;
                inst.vcode = '';
                return;
            }
            inst.errorMsg = '';
            inst._afterLogin(server_data.data);
        });
    
    },

    _afterLogin(data){
        this.initUser(data);
        this.$emit('loginSucc');
        // console.log('--- login widget is ok');
        this.$root.Bus.$emit(Events.LOGIN_SUCC);
    }, 

    getIdentifyCodeMsg(msg){
        this.errorMsg = msg;
    },
      
  }
}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';
    
    .login-box{
        width: 364px;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 6px #dad8d8;
        .input-cls{
            padding: 10px 10px 10px 30px;
            width: 238px;
            border: 1px solid #eaeaea;

            &:focus{
                outline: none;

            }
        }
        .error-t{
            color: red;
            position: absolute;
            top: -24px;
            font-size: 12px;
        }
        .float-img{
            position: absolute;
            top: 8px;
            left:10px;
        }

        .r-1{
            padding-left: 38px;
            padding-top: 10px;
            padding-bottom: 10px;
            font-size: 18px;
            color: $g-main-color;
            border-bottom: 1px solid #eaeaea;
            .duan{
                cursor: pointer;
                color: #000;
                float: right;
                font-size: 12px;
                margin-right: 38px;
                line-height: 25px;
            }
        }
        .box-content{
            width: 282px;
            margin-left: auto;
            margin-right: auto;
            margin-bottom: 50px;
            position: relative;
        }
        .r-2{
            margin-top: 40px;
            position: relative;
        }
        .r-3{
            margin-top: 20px;
            position: relative;
        }
        .r-4{
            font-size: 12px;
            margin-top: 20px;
            .a2{
                color: #333;
                float: right;
            }
            .a1{
                color: $g-main-color;
                margin-right: 120px;
                cursor: pointer;
            }
        }
        .r-5{
            margin-top: 20px;
            text-align: center;

            .login-btn{
                width: 100%;
                background: $g-main-color;
                color: #fff;
                text-align: center;
                cursor: pointer;
                line-height: 38px;
                height: 38px;
                letter-spacing: 4px;

            }
        }
        .r-6{
            margin-top: 33px;
            font-size: 12px;
            color: #999;
            position: relative;
            margin-bottom: 13px;
            .line{
                border-top: solid 1px #dddddd;
                display: block;
                width: 100%;
            }
            .ban{
                position: absolute;
                background-color: #fff;
                width: 150px;
                left: 50%;
                margin-left: -75px;
                text-align: center;
                top: -9px;
                
            }
        }
        .r-7{
            margin-top: 10px;
            margin-left: 80px;
            margin-bottom: 20px;
            img{
                cursor: pointer;
                margin-right: 20px;
            }
            .qq{
                border: solid 1px $g-main-color;
                border-radius: 100%;
                width: 36px;
                height: 36px;
            }
        }
}
/*验证码*/
.check-content{
    border: 1px solid #eaeaea;
    .getmsg{
        background-color: #fff;
        height: 38px;
        display: inline-block;
        padding: 0 10px;
        line-height: 38px;
        width: 128px;
        input{
            border: 0 none;
            width: 106px;
            padding-left: 10px;
            outline: none;
        }
    }
    
    .btn{
        color:$g-main-color;
        text-align: center;
        line-height: 38px;
        width: 108px;
        cursor: pointer;
        float: right;
        width: 124px;
        font-size: 14px;
        color:blue;
        &.invalid{
            // background-color: #ccc;
            color: #ccc;
            cursor: not-allowed;
        }
        &.no-allow{
            color: #ccc;
            cursor: not-allowed;
        }
    }
    .error-tips{
        position: absolute;
        color: red;
        top: -84px;
        font-size: 12px;
        left: 0;
    }
    
}
   
   
</style>
