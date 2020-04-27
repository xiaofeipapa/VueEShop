<template>
    <div>
        <!-- 首页最顶部 查询栏, 购物车.. 这一整横栏 -->
        <div class='the-box'>   
            <div class='left'>
                <img class="logo-img" src="../../assets/image/logo.jpeg" /> 
            </div>
            <div class="right">
                <div class="s-left">


                    <div class='search-box'>
                        
                        <input type="text" class="sec-input"
                            v-model="keyword"
                            maxlength="100" autocomplete="off">
                        
                        <input type="button" class="submit-btn" 
                            @click="_goSearch(keyword)"
                            value="搜 索" id="sbtn">
                        
                        <!-- <a href="javascript:void(0);" id="clear-btn" class="clear-history" title="清除"></a> -->
                        
                    </div>
                    <div style='clear:both'></div>
                    
                    <div class='tag-box'>
                        <span class="item-text" 
                            @click="_goSearch(tag)"
                            v-for="tag in searchTags" 
                            :key="'tag-' + tag">
                            {{tag}}
                        </span>      
                    </div>	

                </div>

                <div class='shopping-cart' @click="_goShopcart">
                    <div class="text">我的购物车
                        <i class="fa fa-shopping-cart"/>
                        <template v-if="productCount > 0">
                            (<span class="red">{{productCount}}</span>)
                        </template>
                        
                        </div>
                </div>
                <div style='clear:both'></div>
                
            </div>
        </div>			
    
    </div>		
</template>

<script>
    import { mapState, mapMutations, mapGetters } from 'vuex'
    import store from '@/store'
    import * as Events from '@/helper/Events';

// =============== 最顶部的banner和查询


export default {
  name: 'app',
    store, 
  data () {
        return {
            keyword: '', 
            searchTags: [
                '护目镜', '螺丝批', '电钻', '电锤', '电工'
            ], 
        }
  }, 
    components: {
                      
    }, 
    computed:{
    
        ...mapGetters('shopStore', [
        'productCount'
        ]),
    }, 

    methods: {

        setKey(q){  
            this.keyword = q;
        },

        _goShopcart(){

            window.location.href = '/views/product/shopcart.html';

        }, 

        _goSearch(q){
            window.location.href = '/views/product/index.html?q=' + q;
        }, 
    }, 
}
</script>

<style lang="scss" scoped>

    @import '../../assets/scss/themeDefault.scss';

    .red {
        color: $g-main-color;
    }

    .the-box {
        width: 1250px;
        margin: 0 auto;
        height: 75px;
        line-height: 75px; 

        .left{
            margin-top: 15px;
            float: left;
            .logo-img {
                width: 109px !important;
                height: 42px ;
            }
        }
        .shop-name{
            float: left;
            color: #999;
            font-size: 24px;
            margin-left: 20px;
        }
        .right{
            float: left;
            display: flex;
            flex-direction: row;
            vertical-align: top;
        }
    }

    .s-left{
    }
    
    .search-box {
        margin-left: 20px;

        .sec-input{

            width: 400px !important;
            padding: 10px 40px 10px 10px;
            border: 1px solid $g-main-color; 
            box-shadow: none;
            border-top-left-radius: 0;
            border-top-right-radius: 2px;
            border-bottom-right-radius: 2px;
            border-bottom-left-radius: 0;
            outline-style: none ;
            outline-width: 0px ;
            text-shadow: none ;
            -webkit-appearance: none ;
            -webkit-user-select: text ;
            outline-color: transparent ;
            box-shadow: none;
            margin-right: 0px;
        }

        .submit-btn{
            width: 100px;
            height: 40px;
            line-height: 40px;
            text-align: center;
            font-size: 16px;
            cursor: pointer;
            right: 0;
            top: 0;
            background-color: $g-main-color;
            color: #fff;
            margin: 0;
            outline: 0;
            border: none;
            padding: 0;
            margin-left: -5px;
        }

    }

    .tag-box {
        margin-left: 20px;
        margin-top: -40px;
        .item-text {
            padding: 4px 5px 4px 5px;
            color: #9c9c9c;
            font-size: 13px;
            cursor: pointer; 

            &:hover{
                color: $g-main-color;
            }
        }
    }
        

    .shopping-cart {
        color: $g-main-color;
        margin-left: 40px; 
        border: 1px solid #eaeaea;
        height: 38px;
        margin-top: 20px;
        border-radius: 2px;
        cursor: pointer;
        .text{
            margin-top: -19px;
            padding: 0 20px;

            i {
                font-size: 24px;
                
            }
            .red{
                color: $g-main-color;
            }
        }
        
    }
</style>
