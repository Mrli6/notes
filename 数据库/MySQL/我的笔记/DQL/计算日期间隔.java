在mysql当中怎么计算两个日期的“年差”，差了多少年？
	TimeStampDiff(间隔类型, 前一个日期, 后一个日期)
	
	timestampdiff(YEAR, hiredate, now())

	间隔类型：
		SECOND   秒，
		MINUTE   分钟，
		HOUR   小时，
		DAY   天，
		WEEK   星期
		MONTH   月，
		QUARTER   季度，
		YEAR   年