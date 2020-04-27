<template>


    <div class="toolbar-wrap">
        <a class="user-home" href="/views/user.html" target="_blank"><span class="toolbar-pg"></span><span class="span">个人中心</span></a>
        <a class="shop-car" href="/views/cart/index.html" target="_blank">
          <span class="toolbar-pg"></span><span class="span">购物车</span>
          <div class="s-qu">({{productCount}})</div>
        </a>
        <a class="contact" @click="_contact"><span class="toolbar-pg"></span><span class="span">联系客服</span></a>
        <a class="go-to-top" @click="_goTop()"><span class="toolbar-pg"></span><span class="span">回到顶部</span></a>
    </div>

</template>

<script>
import store from '@/store'
import { mapState, mapMutations, mapGetters } from 'vuex'

// 侧边栏工具条
export default {
    name: 'Toolbar',
    store, 
    components:{
        
    },
    data () {
        return {
            
            
        }
    },
    methods: {
        _goTop(){
            
            document.documentElement.scrollTop = 0;
            document.body.scrollTop = 0;
            document.querySelector('#app').scrollIntoView({
            behavior:'smooth'
            })
        },
        
        _contact(){
            document.getElementById("nb_icon_wrap").click();
        }
    }
    ,computed:{
    
        ...mapGetters('shopStore', [
            'productCount'
        ]),
    }

}
</script>

<style lang="scss" scoped type="text/css">

    @import '../../assets/scss/themeDefault.scss';

    .toolbar-wrap{
    position:fixed;
    height:100%;
    width:36px;
    background-color:#333;
    right: 0;
    top: 0;
    z-index: 10000;
    cursor: pointer;
    > a{
        width: 36px;
        height: 32px;
        display: block;
        margin-bottom: 30px;
        position: relative;
        &:hover{
            .span{
            left: -84px;
            }
            .toolbar-pg{
            background-color:$g-main-color !important;
            &::before{
                content: '';
                position: absolute;
                border-color: transparent transparent transparent #e83820;
                border-style: solid;
                border-width: 5px;
                left: 0;
                top: 12.5px;
            }
        }
        }
        .span{
        position: absolute;
        white-space: nowrap;
        background-color:#e83820;
        color: #fff;
        font-size: 13px;
        top: 0;
        left: 0;
        z-index: -1;
        line-height: 32px;
        width: 84px;
        text-align: center;
        border-radius: 3px 0 0 3px;
        transition: all .5s ease;
        }
    }
    .toolbar-pg{
        display: block;
        width: 100%;
        height: 100%;
        position: relative;
        
    }
    .contact{
        .toolbar-pg{
        background:#333 url(../../assets/image/contact.png) center center no-repeat;
        }
    }
    .user-home{
        margin-top: 380px;
        .toolbar-pg{background:#333 url(../../assets/image/small_user_02.png) center center no-repeat;}
    }
    .shop-car{
        .toolbar-pg{
        background:#333 url(../../assets/image/shopcar.png) center center no-repeat;
        }
    }
    
    .go-to-top{
        .toolbar-pg{
        background:#333 url(../../assets/image/top.png) center center no-repeat;
        }
        margin-top: 60px;
    }
    }

    .s-qu{
        color: #fff;
        text-align: center;
        font-size: 12px;
    }

</style>
