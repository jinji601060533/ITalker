package net.qiujuer.italker.common.app;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiujuer
 */

public abstract class   Fragment extends androidx.fragment.app.Fragment {
    protected View mRoot;
    //unbinder是butterKnife.bind返回的  一般在onDestroy中调用,Unbinder.unbind()方法是接触绑定的意思
    protected Unbinder mRootUnBinder;

    /**
     * fragment被调用的时候，最先被调用的方法是onAttach，比onCreate还要优先
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 初始化参数，这个函数是什么东西？？
        initArgs(getArguments());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /**
         *  业务逻辑：如果root已经存在，即父布局不为空的情况下初始化；如果父布局已经存在，则删除掉父布局
         */

        if (mRoot == null) {
            int layId = getContentLayoutId();
            // 初始化当前的根布局，但是不在创建时就添加到container里边 ,最后一个参数是F的含义就是不添加到Root中
            View root = inflater.inflate(layId, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除，因为调度过后，父布局已经存在，重新初始化的时候可能mroot还没有被回收
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 当View创建完成后初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     */
    protected void initArgs(Bundle bundle) {

    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件  this：当前  root：目标绑定在root下
     */
    protected void initWidget(View root) {
        mRootUnBinder = ButterKnife.bind(this, root);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回按键触发时调用
     *
     * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
     * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
     */
    public boolean onBackPressed() {
        return false;
    }

}
