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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.bocsoft.common.base.service.BaseService;
import com.bocsoft.common.model.DataDictionary;
import com.bocsoft.common.vo.TreeNode;

/**
 * 数据字典
 * @author bocsoft
 *
 */
public class DataDictionaryService extends BaseService {

	private DataDictionary dao=new DataDictionary().dao();

	/* (non-Javadoc)
	 * @see com.bocsoft.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}

	/**
	 * 数据字典树
	 * @return
	 */
	public Collection<TreeNode> getDictionaryTree() {
		List<Record> list = queryAllList("order by order_num asc");
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Record record : list) {
			TreeNode node = new TreeNode();		
			node.setId(record.getStr("code"));
			node.setText(record.getStr("name"));
			Collection<TreeNode> children =new ArrayList<TreeNode>();
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}
	
}
