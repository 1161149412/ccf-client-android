package cn.cnlinfo.ccf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.cnlinfo.ccf.R;


/**
 * Created by cj on 2016/9/21.
 */
public class SureTipDialog extends Dialog {

    public SureTipDialog(Context context) {
        super(context);
    }

    public SureTipDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private int titleIcon;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private int positiveTextColor;
        private int negativeTextColor;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Set the Dialog message from String
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog Icon from resource
         *
         * @param icon
         * @return
         */
        public Builder setIcon(int icon) {
            this.titleIcon = icon;
            return this;
        }


        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
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
                                         int positiveTextColor,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveTextColor = positiveTextColor;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         int negativeTextColor,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeTextColor = negativeTextColor;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public SureTipDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final SureTipDialog dialog = new SureTipDialog(context, R.style.ACPLDialog);
            View layout = inflater.inflate(R.layout.dialog_sure, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            // set the dialog title
            ((ImageView) layout.findViewById(R.id.iv_icon)).setImageResource(titleIcon);
            // set the confirm button
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.bt_positive_button))
                        .setText(positiveButtonText);
                ((Button) layout.findViewById(R.id.bt_positive_button))
                        .setTextColor(context.getResources().getColor(positiveTextColor));
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.bt_positive_button))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.bt_positive_button).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.bt_negative_button))
                        .setText(negativeButtonText);
                ((Button) layout.findViewById(R.id.bt_negative_button))
                        .setTextColor(context.getResources().getColor(negativeTextColor));
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.bt_negative_button))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.bt_negative_button).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.tv_content)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.ll_content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.ll_content))
                        .addView(contentView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
