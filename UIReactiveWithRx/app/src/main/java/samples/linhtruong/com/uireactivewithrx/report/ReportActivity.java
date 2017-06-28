package samples.linhtruong.com.uireactivewithrx.report;

import android.os.Bundle;

import samples.linhtruong.com.uireactivewithrx.utils.ActionBar;
import samples.linhtruong.com.uireactivewithrx.utils.base.BaseActionActivity;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 3/5/17 - 11:34.
 * @organization VED
 */

public class ReportActivity extends BaseActionActivity {

    private ReportPresenter mPresenter;
    private ReportView mView;

    @Override
    protected void onCreateContent(Bundle bundle) {

    }

    @Override
    protected void setupActionBar(ActionBar.Builder builder) {
        super.setupActionBar(builder);
        builder.withBackButton(true);
        builder.withTitle("Report");
    }

    @Override
    protected void initDependency() {

    }
}
