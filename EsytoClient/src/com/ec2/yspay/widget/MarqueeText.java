/*
 * 类文件名:  MarqueeText.java
 * 著作版权:  深圳市易商云电子商务有限公司 Copyright 2012-2022, E-mail: hongxiang_luo@esyto.com, All rights reserved
 * 功能描述:  <描述>
 * 类创建人:  罗洪祥
 * 创建时间:  2015年12月2日
 * 功能版本:  V001Z0001
 */
package com.ec2.yspay.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 跑马灯效果的Textview
 * 
 * @author   罗洪祥
 * @version  V001Z0001
 * @date     2015年12月2日
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MarqueeText extends TextView {
    public MarqueeText(Context con) {
         super(con);
    }

      public MarqueeText(Context context, AttributeSet attrs) {
         super(context, attrs);
      }
      public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
         super(context, attrs, defStyle);
      }
      @Override
      public boolean isFocused() {
          return true;
      }
      @Override
      protected void onFocusChanged(boolean focused, int direction,
         Rect previouslyFocusedRect) {  
      }
}

