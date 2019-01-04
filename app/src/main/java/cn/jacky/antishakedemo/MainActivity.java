package cn.jacky.antishakedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jacky.antishakedemo.aop.AntiShakeClick;

/**
 * 按钮点击防抖案例
 *
 * @author HZJ
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt1)
    Button mBt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        //1普通点击事件绑定，注解不需要赋值id，只要点击事件里有View 作为参数即可
        mBt1.setOnClickListener(new View.OnClickListener() {
            @AntiShakeClick
            @Override
            public void onClick(View v) {
                jumpToSecond();
            }
        });
    }

    /**
     * 2配合butterknife点击事件绑定，注解一定要赋值按钮的id,因为黄油刀点击方法中默认没有View参数，拿不到id，
     * 你也可以自己加上View参数，这样也不用写viewId
     * 就像这样：
     *
     * @AntiShakeClick()
     * @OnClick(R.id.bt2) public void onBt2Clicked(View view) {
     * <p>
     * }
     */
    @AntiShakeClick(viewId = R.id.bt2)
    @OnClick(R.id.bt2)
    public void onBt2Clicked() {
        jumpToSecond();
    }

    /**
     * 没有加防抖的按钮
     */
    @OnClick(R.id.bt3)
    public void onBt3Clicked() {
        jumpToSecond();
    }

    private void jumpToSecond() {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
