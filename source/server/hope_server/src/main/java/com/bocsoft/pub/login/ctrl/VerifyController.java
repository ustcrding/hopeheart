/**
 * Copyright Bocsoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.bocsoft.pub.login.ctrl;

import com.jfinal.core.Controller;
import com.bocsoft.common.kit.VerifyCodeKit;
import com.bocsoft.common.routes.ControllerBind;

/**
 * 验证码
 * 
 * @author bocsoft
 *
 */
@ControllerBind(path="/pub/verify")
public class VerifyController extends Controller {

	public void index() {
		VerifyCodeKit.createImage(getResponse(), 2);
		getSession().setAttribute("verifyCode", VerifyCodeKit.getVerityCode(2));
		renderNull();
	}
}
