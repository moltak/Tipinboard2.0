package com.corping.taskboard;

import java.text.SimpleDateFormat;

public class BasicInfo {

	/**
	 * ���� �޸� �н�
	 */
	public static String ExternalPath = "/sdcard/";

	/**
	 * ���� �޸� �н� üũ ����
	 */
	public static boolean ExternalChecked = false;

	/**
	 * ���� ���� ��ġ
	 */
	public static String FOLDER_PHOTO 		= "MultimediaMemo/photo/";

	/**
	 * ������ ���� ��ġ
	 */
	public static String FOLDER_VIDEO 		= "MultimediaMemo/video/";

	/**
	 * ���� ���� ��ġ
	 */
	public static String FOLDER_VOICE 		= "MultimediaMemo/voice/";

	/**
	 * �ձ۾� ���� ��ġ
	 */
	public static String FOLDER_HANDWRITING 	= "MultimediaMemo/handwriting/";

	/**
	 * �̵�� ����
	 */
	public static final String URI_MEDIA_FORMAT		= "content://media";

	/**
	 * �����ͺ��̽� �̸�
	 */
	public static String DATABASE_NAME = "MultimediaMemo/memo.db";


	//========== ����Ʈ �ΰ����� ����� ���� Ű�� ==========//
	public static final String KEY_MEMO_MODE = "MEMO_MODE";
	public static final String KEY_MEMO_TEXT = "MEMO_TEXT";
	public static final String KEY_MEMO_ID = "MEMO_ID";
	public static final String KEY_MEMO_DATE = "MEMO_DATE";
	public static final String KEY_ID_PHOTO = "ID_PHOTO";
	public static final String KEY_URI_PHOTO = "URI_PHOTO";
	public static final String KEY_ID_VIDEO = "ID_VIDEO";
	public static final String KEY_URI_VIDEO = "URI_VIDEO";
	public static final String KEY_ID_VOICE = "ID_VOICE";
	public static final String KEY_URI_VOICE = "URI_VOICE";
	public static final String KEY_ID_HANDWRITING = "ID_HANDWRITING";
	public static final String KEY_URI_HANDWRITING = "URI_HANDWRITING";


	//========== �޸� ��� ��� ==========//
	public static final String MODE_INSERT = "MODE_INSERT";
	public static final String MODE_MODIFY = "MODE_MODIFY";
	public static final String MODE_VIEW = "MODE_VIEW";


	//========== ��Ƽ��Ƽ ��û �ڵ�  ==========//
	public static final int REQ_VIEW_ACTIVITY = 1001;
	public static final int REQ_INSERT_ACTIVITY = 1002;
	public static final int REQ_PHOTO_CAPTURE_ACTIVITY = 1501;
	public static final int REQ_PHOTO_SELECTION_ACTIVITY = 1502;
	public static final int REQ_VIDEO_RECORDING_ACTIVITY = 1503;
	public static final int REQ_VIDEO_LOADING_ACTIVITY = 1504;
	public static final int REQ_VOICE_RECORDING_ACTIVITY = 1505;
	public static final int REQ_HANDWRITING_MAKING_ACTIVITY = 1506;



	//========== ��¥ ����  ==========//
	public static SimpleDateFormat dateDayNameFormat = new SimpleDateFormat("yyyy�� MM�� dd��");
	public static SimpleDateFormat dateDayFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateNameFormat = new SimpleDateFormat("yyyy�� MM�� dd�� HH�� mm��");
	public static SimpleDateFormat dateNameFormat2 = new SimpleDateFormat("yyyy-MM-dd HH�� mm��");


	//========== ��ȭ���� Ű��  ==========//
	public static final int WARNING_INSERT_SDCARD = 1001;
	public static final int IMAGE_CANNOT_BE_STORED = 1002;

	public static final int CONTENT_PHOTO = 2001;
	public static final int CONTENT_VIDEO = 2002;
	public static final int CONTENT_VOICE = 2003;
	public static final int CONTENT_HANDWRITING = 2004;
	public static final int CONTENT_PHOTO_EX = 2005;
	public static final int CONTENT_VIDEO_EX = 2006;
	public static final int CONTENT_VOICE_EX = 2007;
	public static final int CONTENT_HANDWRITING_EX = 2008;

	public static final int CONFIRM_DELETE = 3001;

	public static final int CONFIRM_TEXT_INPUT = 3002;



	public static boolean isAbsoluteVideoPath(String videoUri) {
    	if(videoUri.startsWith(URI_MEDIA_FORMAT)) {
    		return false;
    	} else {
    		return true;
    	}
    }

}
