package com.boc.service;

import com.boc.model.Psyknowledge;
import com.boc.model.Rescueknowledge;

public class KnowledgeService {
	public Rescueknowledge queryRescueKnowledge(String rescueType,String rescueSubType){
		return Rescueknowledge.dao.findFirst("select * from rescueknowledge where rescue_type=? and rescue_subtype=?",rescueType,rescueSubType);
	}
	public Psyknowledge queryPsyKnowledge(String psyledgeType,String psyledgeSubtype){
		return Psyknowledge.dao.findFirst("select * from psyknowledge where psyledge_type=? and psyledge_subtype=?",psyledgeType,psyledgeSubtype); 
	}
}
