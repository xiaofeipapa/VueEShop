<template>
    <span>
        <span class="expire-text" v-if="expire">
            {{text}}已超时
        </span>
        <span class="time-info" v-else
            >{{day}} 天 : {{hour}} 时 : {{min}} 分 : {{sec}} 秒</span>

    </span>
</template>

<script>
    import * as Events from '@/helper/Events';

    export default {

        props: {
            'text' : {
                'type': String, 
                'default': '订单', 
            }, 
        }, 
        
        components: {
            
        }, 
        data() {
            return {
                
                day: 0, 
                hour: 0, 
                min: 0, 
                sec: 0, 

                endTime: '', // 外部设置

                expire: false,  // 超时

            };
        },

        computed: {


        }, 

        methods: {

            isExpire(){
                return this.expire;
            }, 
            _countdown(){

                if(this.expire){
                    return;
                }
                
                const now = Date.parse(new Date());
                const end = Date.parse(new Date(this.endTime));

                //时间差  
                var leftTime = end-now; 
                if(leftTime < 1){
                    this.expire = true;
                    this.$emit('expire');
                    return;
                }

                // console.log('--- leftTime: ', leftTime);

                //定义变量 d,h,m,s保存倒计时的时间  
                var d,h,m,s;  
                if (leftTime>=0) {  
                    d = Math.floor(leftTime/1000/60/60/24);  
                    h = Math.floor(leftTime/1000/60/60%24);  
                    m = Math.floor(leftTime/1000/60%60);  
                    s = Math.floor(leftTime/1000%60);                     
                }

                this.day = d;
                this.hour = h;
                this.min = m;
                this.sec = s;

                let inst = this;
                setTimeout(function () {
                    inst._countdown()
                }, 1000)


            }, 

            _doStart(vo){
                let endTime = vo.endTime;
                if(!endTime )return;

                this.endTime = endTime;
                this._countdown();
            }, 


        },
        
        mounted() {
            let event = Events.CHILD_INIT;
            this.$root.Bus.$off(event).$on(event, this._doStart);
        }, 
    }
        
</script>

<style lang="scss" scoped type="text/css">

    .time-info{
        color: #ff0000;
        padding: 5px 10px 5px 10px;
        font-size: 24px;
    }

    .expire-text{
        font-size: 24px;
        padding: 5px 10px 5px 10px;
        color: #999;
    }

</style>