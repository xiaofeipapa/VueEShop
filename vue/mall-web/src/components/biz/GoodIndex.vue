<template>
    <!-- 首页最顶部 工具栏. 地域选择, 我的订单.. 这一整横栏 -->
    <div class='box-wrapper'>
        <div class='actual-box' v-if="dataList.length > 0">
            <div class="floor-box" v-if="showTitle"> 
                精选
            </div>
            <div class="prod-box" > 
                <div class='each-box' v-for="prod in dataList" 
                    @click="_goDetail(prod.id)"
                    :key="'good-' + prod.id">
                    <div class="img-box">
                        <img :src="prod.productImage">
                    </div>
                    <div class="title">{{prod.modalTitle}}</div>
                    <div class="price-box">
                        ￥ {{prod.retailPrice}} 元
                    </div>
                </div>  
            </div>
            <div style="clear:both"></div>
            <!-- 分页条沿用已有控件 -->
            <Pagination :pageCount="pageCount" 
                :pageNo="pageNo" @pageNo="_changePageAndGo">
            </Pagination>
            <div style="clear:both"></div>
        </div>
        <div class="no-p" v-else>
            没有数据
        </div>
        <div style="clear:both"></div>
    </div>
</template>

<script>

// =============== 商品的列表, 带自动下拉分页功能
import * as Ajax from '@/helper/Ajax';
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';
import Pagination from 'components/com/Pagination';


export default {

    props: {
        'showTitle': {
            type: Boolean, default: true, 
        }
    }, 

  data () {
        return {
            dataList: [],        
            pageNo: 1, 
            pageSize: 20, 
            pageCount: 0, 
            keyword: '',    // 查询关键字
            cat: '',        // 分类id
        }
  }, 
    components: {
        Pagination                
    }, 

    methods: {

        _goDetail (pid){
            
            window.open('/views/product/detail.html?pid=' + pid);
        }, 

    
        _changePageAndGo(value){
            this.pageNo = value;
            this.loadData();
        },

        loadData (queryUrl){

            if(queryUrl){
                let keyword = VueHelper.getUrlParam(queryUrl, 'q');
                if(keyword){
                    keyword = decodeURI(keyword);
                    this.keyword = keyword;
                }

                let cat = VueHelper.getUrlParam(queryUrl, 'c');
                if(cat)this.cat = cat;

                  console.log('--- queryUrl: ', queryUrl);
                  console.log('--- keyword: ', keyword);
            }

            let url = Urls.product_index;

            let params = {
                'pageNo': this.pageNo, 
                'pageSize': this.pageSize, 
                'q': this.keyword, 
                'cat': this.cat, 
            }
            this._loadInternal(url, params);
        }, 
        loadLikeGoods (){

            let url = Urls.product_getLikePage;

            let params = {
                'pageNo': this.pageNo, 
                'pageSize': this.pageSize, 
            }
            this._loadInternal(url, params);
        }, 

        _loadInternal(url, params){

            let inst = this;
            
            Ajax.post(url, params, function(serverData){
                let page = serverData.data;
                inst.dataList = page.dataList;
                inst.pageCount = page.pageCount;
            });
        }, 
    }, 

    mounted(){
        
    }, 
}
</script>

<style lang="scss" scoped>

    @import '../../assets/scss/themeDefault.scss';

    .box-wrapper {
        width: 100%;
        position: relative;
    }

    .no-p {
        margin: 0 auto; 
        width: 1250px;
        text-align: center;
        color: #999;
        margin-bottom: 40px;
    }

    .actual-box {
        margin: 0 auto; 
        width: 1250px;
        margin-bottom: 40px;

        .floor-box{
            font-size: 24px;
            text-align: center;
            color: #666;
            margin-bottom: 10px;
        }

        .prod-box{
            position: relative;
        }

        .each-box{
            float: left;
            width: 200px;
            height: 320px;
            padding: 10px 15px 10px 15px;
            border: 1px solid #fff;
            border-radius: 2px;
            margin-bottom: 20px;
            cursor: pointer;

            &:hover{
                border: 1px solid $g-main-color;
            }

            .img-box{
                text-align: center;

                img {
                    width: 180px !important;
                    height: 180px;
                }
            }

            .title {
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow-wrap: break-word;
                height: 40px;
                color: #666;
                font-size: 14px;
                margin-top: 40px;
            }

            .price-box{
                margin-top: 5px;
                font-size: 18px;
                color: $g-red;
            }
        }
    }
    
</style>
