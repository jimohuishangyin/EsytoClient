package com.ec2.yspay.widget;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.ec2.yspay.R;

public class PayLoadingDialog extends Dialog {

    public PayLoadingDialog(Context context) {
        super(context);
    }

    public PayLoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String positiveButtonText;
        private String contentMsg;
        private OnClickListener positiveButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }


        public String getContentMsg() {
            return contentMsg;
        }

        public void setContentMsg(String contentMsg) {
            this.contentMsg = contentMsg;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        /**
         * getRemarkText
         *
         * @param title
         * @return
         */

        public PayLoadingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final PayLoadingDialog dialog = new PayLoadingDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.popup_warn_pay_loading_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            // set the confirm button
            if(contentMsg!=null){
                ((TextView) layout.findViewById(R.id.message))
                        .setText(contentMsg);
            }
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.positiveButton))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }

            dialog.setContentView(layout);
            return dialog;
        }

    }
}
