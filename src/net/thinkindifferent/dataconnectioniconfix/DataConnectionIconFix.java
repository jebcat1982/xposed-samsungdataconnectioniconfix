package net.thinkindifferent.dataconnectioniconfix;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.*;

public class DataConnectionIconFix implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if(!lpparam.packageName.equals("com.android.systemui"))
			return;
		
		Class<?> NetworkController = findClass("com.android.systemui.statusbar.policy.NetworkController", lpparam.classLoader);
		Class<?> Operator = null;
		for(Class<?> c : NetworkController.getDeclaredClasses())
			if(c.getSimpleName().equals("Operator"))
				Operator = c;
		findAndHookMethod(Operator, "getOperatorFromString", java.lang.String.class, XC_MethodReplacement.returnConstant(getStaticObjectField(Operator, "OPEN")));
	}

}
