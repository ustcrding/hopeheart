#sql("getPsytestInfo")
	SELECT victimtest_id, tests_level,counseling,testioin_date,address_code FROM psytestinfo WHERE 1=1
	#if(victimId)
		AND VICTIM_ID = #para(victimId)
	#end
	#if(addressCode)
		AND address_code = #para(addressCode)
	#end
	#if(testStartDate)
		AND testioin_date >=#para(testStartDate)
	#end
	#if(testEndDate)
		AND testioin_date <=#para(testEndDate)
	#end
	ORDER BY testioin_date,testjoin_time desc
#end

#sql("getPsyGuidenceInfo")
	SELECT t1.victim_id victimId,t2.victim_name victimName,t2.victim_gender victimGender,t1.victimtest_id victimtestId,t1.tests_level testsLevel,t1.testioin_date testioinDate,
	t1.address_code addressCode
	from psytestinfo t1,victiminfo t2 where t1.victim_id=t2.victim_id
	#if(doctorId)
		AND t1.psychologist_id = #para(doctorId)
	#end
	#if(addressCode)
		AND t1.address_code = #para(addressCode)
	#end
	#if(testStartDate)
		AND t1.testioin_date >=#para(testStartDate)
	#end
	#if(testEndDate)
		AND t1.testioin_date <=#para(testEndDate)
	#end
	ORDER BY t1.testioin_date DESC
#end

#sql("getPsytestDetail")
	select victimtest_id victimtestId,ifnull(tests_level,'') testsLevel,ifnull(a.counseling,'') counseling,
                ifnull(testioin_date,'') testioinDate,ifnull(testjoin_time,'') testjoinTime,
                ifnull(a.address_code,'') addressCode,ifnull(a.satisficing,'')satisficing,ifnull(doctor_name,'') doctorName,
                ifnull(certificate,'') certificate,ifnull(affiliate,'') affiliate,ifnull(volunteer_name,'') volunteerName,ifnull(platform,'') platform
        FROM PSYTESTINFO a LEFT JOIN DOCTORINFO b ON a.PSYCHOLOGIST_ID=b.DOCTOR_ID
        LEFT JOIN VOLUNTEERINFO c ON a.VOLUNTEER_ID=c.VOLUNTEER_ID
        WHERE a.VICTIM_ID=#para(victimId) AND a.VICTIMTEST_ID=#para(victimTestId)
#end
