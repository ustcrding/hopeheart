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

package com.bocsoft.portal.core.service;

import com.jfinal.plugin.activerecord.Model;
import com.bocsoft.common.base.service.BaseService;
import com.bocsoft.common.model.DataDictionaryValue;

/**
 * 字典数据值
 * @author bocsoft
 *
 */
public class DataDictionaryValueService extends BaseService {

	private DataDictionaryValue dao=new DataDictionaryValue().dao();

	/* (non-Javadoc)
	 * @see com.bocsoft.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}

}
