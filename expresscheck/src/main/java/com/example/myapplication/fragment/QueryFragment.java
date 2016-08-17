package com.example.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity.ExpInfo;
import com.example.myapplication.activity.QueryResultsActivity;
import com.zxingdemo.android.CaptureActivity;

/**
 * Created by 59427 on 2016/6/24.
 */
public class QueryFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE_SCAN = 1;
    private static final String DECODED_CONTENT_KEY = "codedContent";

    private TextView et_name;
    private EditText et_num;
    private Button btn_query;
    private String num;
    private String name;
    private ImageButton del;
    private ImageButton delete_name;
    private EditText et_goods;
    private String expName;
    private String imgUrl;
    private ImageButton scanner;
    private String simpleName;
    private TextView tv_hint;
    private ImageButton delete_num;
    private ImageButton imgbtn_like;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        del = (ImageButton) view.findViewById(R.id.del);
        delete_name = (ImageButton) view.findViewById(R.id.delete_name);
        et_name = (TextView) view.findViewById(R.id.et_name);
        et_num = (EditText) view.findViewById(R.id.et_num);
        btn_query = (Button) view.findViewById(R.id.btn_query);
        et_goods = (EditText) view.findViewById(R.id.et_goods);
        scanner = (ImageButton) view.findViewById(R.id.scanner);
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        delete_num= (ImageButton) view.findViewById(R.id.delete_num);
        imgbtn_like= (ImageButton) view.findViewById(R.id.imgbtn_like);
        scanner.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        del.setOnClickListener(this);
        et_name.setOnClickListener(this);
        delete_num.setOnClickListener(this);
        imgbtn_like.setOnClickListener(this);
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                delete_num.setVisibility(View.VISIBLE);
                String num = et_num.getText().toString();
                if (num.length() == 0) {
                    delete_num.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_goods.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                del.setVisibility(View.VISIBLE);
                tv_hint.setVisibility(View.INVISIBLE);
                String goods = et_goods.getText().toString();
                if (goods.length() == 0) {
                    del.setVisibility(View.INVISIBLE);
                    tv_hint.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                delete_name.setVisibility(View.VISIBLE);
                name = et_name.getText().toString();
                if (name.length() == 0) {
                    delete_name.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        delete_name.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                num = et_num.getText().toString().trim();
                name = et_name.getText().toString();
                if (!TextUtils.isEmpty(num) && !TextUtils.isEmpty(name)) {
                    Intent intent = new Intent(getActivity(), QueryResultsActivity.class);
                    intent.putExtra("num", num);
                    intent.putExtra("simpleName", simpleName);
                    intent.putExtra("expName", expName);
                    intent.putExtra("imgUrl", imgUrl);
                    startActivity(intent);
                } else {
                    Toast toast = new Toast(getContext());
                    View view=View.inflate(getContext(),R.layout.toast_custom,null);
                    toast.setView(view);
                    toast.show();
                }
                break;
            case R.id.et_name:
                Intent intent = new Intent(getActivity(), ExpInfo.class);
                startActivityForResult(intent, 1);
            case R.id.del:
                et_goods.setText("");
                del.setVisibility(View.INVISIBLE);
                break;
            case R.id.delete_name:
                et_name.setText("");
                delete_name.setVisibility(View.INVISIBLE);
                break;
            case R.id.scanner:
                Intent intent1 = new Intent(getActivity(),
                        CaptureActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SCAN);
            case R.id.delete_num:
                et_num.setText("");
            case R.id.imgbtn_like:
                imgbtn_like.startAnimation(AnimationUtils.loadAnimation(
                        getContext(), R.anim.test4));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN ) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                if(content!=null){
                    et_num.setText(content);
                }
                Bundle bundle = data.getExtras();
                simpleName = bundle.getString("simpleName");
                expName = bundle.getString("expName");
                imgUrl = bundle.getString("imgUrl");
                et_name.setText(expName);

            }
        }

    }

}
