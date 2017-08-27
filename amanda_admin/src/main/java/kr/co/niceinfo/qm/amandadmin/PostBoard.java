package kr.co.niceinfo.qm.amandadmin;

import java.io.File;
import java.util.Date;

import kr.co.niceinfo.qm.amandadmin.data.db.DbHelper;
import kr.co.niceinfo.qm.amandadmin.data.db.FirebaseDbHelper;
import kr.co.niceinfo.qm.amandadmin.data.db.model.Board;

public class PostBoard {
	public static void main(String[] args)  {
		try {
			
			DbHelper db = new FirebaseDbHelper(FirebaseDbHelper.DB_URL, new File(FirebaseDbHelper.CERTIFICATE_FILE_URL));
			
			for(int i = 0; i < 10; i++) {
				Board board = new Board();
				board.setPostingTitle("공지사항 #" + (i+1));
				board.setPostingContent("공지사항 #" + (i+1) + " 내용입니다.");
				board.setRegDt(new Date().toString());
				
				db.add(board);
			}
			

		}
		catch(Throwable t) {
			t.printStackTrace();
		}
	}
	
}