<template>
    <!-- 楼层 -->
    <div class='box-wrapper'>
        <div class='actual-box' v-for="data in dataList" :key="'ff-' + data.id">
            <div class="floor-box"> 
                {{data.name}}
            </div>
            <div class="prod-box"> 
                <div class='each-box' 
                    @click="_goDetail(prod.id)"
                    v-for="prod in data.products" :key="'rg-' + prod.id">
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
        </div>
        <div style="clear:both"></div>
    </div>
</template>

<script>

// =============== 商品的列表, 带自动下拉分页功能
import * as Ajax from '@/helper/Ajax';
import * as VueHelper from '@/helper/VueHelper';
import * as Urls from '@/helper/Urls';

export default {

  data () {
        return {
            dataList: [],        
        }
  }, 
    components: {
                     
    }, 

    methods: {

        _goDetail (pid){
            
            window.open('/views/product/detail.html?pid=' + pid);
        }, 


        indexSearch (){
            
            let inst = this;

            let url = Urls.homeIndex_getAllHomeFloor;
            let params = {
            }
            
            Ajax.post(url, params, function(serverData){
                    inst.dataList = serverData.data;
            });
        }, 
    }, 

    mounted(){
        this.indexSearch();
    }, 
}
</script>

<style lang="scss" scoped>

    @import '../../../assets/scss/themeDefault.scss';

    .box-wrapper {
        width: 100%;
        position: relative;
    }

    .actual-box {
        margin: 0 auto; 
        width: 1250px;

        .floor-box{
            font-size: 24px;
            font-weight: bold;
            border-left: 5px solid $g-main-color;
            padding-left: 10px;
            margin-bottom: 10px;
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
