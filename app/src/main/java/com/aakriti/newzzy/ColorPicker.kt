package com.aakriti.newzzy

object ColorPicker{
     val colors=arrayOf("#3EB9DF","#3685BC","#FF43A26D","#FFC28132","#FF32A0C2","#FFC65374","#FFBFA053","#FF61BDB2","#FFB8584E","#FF806EC1","#FF765A98")
    var colorIndex=1
    fun getColor():String{
        return colors[colorIndex++ % colors.size]
    }
}