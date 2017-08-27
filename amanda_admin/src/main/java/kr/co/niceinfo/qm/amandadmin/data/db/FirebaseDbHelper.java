package kr.co.niceinfo.qm.amandadmin.data.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import kr.co.niceinfo.qm.amandadmin.data.db.model.Board;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDbHelper implements DbHelper {
	public static final String DB_URL = "https://amanda-3c619.firebaseio.com/";
	public static final String CERTIFICATE_FILE_URL = "amanda-3c619-firebase-adminsdk-7vrb7-6a1d3a1ad1.json";
	
	public static final String REFERENCE = "boards";
	private DatabaseReference ref;
	
	public DatabaseReference getRef() {
		return this.ref;
	}
	
	public FirebaseDbHelper(String database,File sa) throws FileNotFoundException {
		// Fetch the service account key JSON file contents
		FileInputStream serviceAccount = new FileInputStream(sa);

		// Initialize the app with a service account, granting admin privileges
		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
		    .setDatabaseUrl(database)
		    .build();
		FirebaseApp.initializeApp(options);

		// As an admin, the app has access to read and write all data, regardless of Security Rules
		this.ref = FirebaseDatabase.getInstance().getReference(REFERENCE);
	}
	
	public void add(Board board) {
		DatabaseReference pagerRef = ref.child("board");
		
		String key = pagerRef.push().getKey();
		board.setKey(key);
		pagerRef.child(key).setValue(board);
	}
	
	public static void main(String[] args)  {
		try {
			
			FirebaseDbHelper db = new FirebaseDbHelper(DB_URL, new File(CERTIFICATE_FILE_URL));
			
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