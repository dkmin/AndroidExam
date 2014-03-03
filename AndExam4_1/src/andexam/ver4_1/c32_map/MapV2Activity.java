package andexam.ver4_1.c32_map;

import andexam.ver4_1.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * 1. 환경 설정
 * 1) google-play-service library import 하기
 * 2) https://code.google.com/apis/console 에 가서 api 키 등록
 * 3) 추가적인 퍼미션 설정 :  v2는 meta-data에  키 설정
 *    - manefest의 manefest extra 탭 영역에 uses feature 에 open gl 2.0이 아니면 설치안되도록 설정
 *    - manefest의 application 탭 영역에 add 후 meta data 추가
 *      name : com.google.android.maps.v2.API_KEY
 *      value : 2번에서 얻은 api 키 값
 *    - manefest permission
 *      name : [패키지이름].permission.MAPS_RECEIVE
 *      protection level : signature
 *    - manefest uses permission 추가
 *      [패키지이름].permission.MAPS_RECEIVE
 *      android.permission.INTERNET
        android.permission.WRITE_EXTERNAL_STORAGE
        android.permission.ACCESS_COARSE_LOCATION
        android.permission.ACCESS_FINE_LOCATION
        com.google.android.providers.gsf.permission.READ_GSERVICES
*/
public class MapV2Activity extends Activity {
    
    GoogleMap mGoogleMap;
	
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//2. fragment 설정
		setContentView(R.layout.activity_map_v2);
		
		//3. 카메라 설정 : 초기 위치 값과 zoom 레벨 설정
		FragmentManager fm = getFragmentManager();
		MapFragment mMapFragment = (MapFragment)fm.findFragmentById(R.id.mapframent);
		mGoogleMap = mMapFragment.getMap();
		
		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.481828, 126.880001), 15));
		
		//4. 마커 표시하기
		Bitmap mBitmap = overlay(BitmapFactory.decodeResource(getResources(), R.drawable.pin),
		        BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
		mGoogleMap.addMarker(new MarkerOptions()
		            .position(new LatLng(37.481828, 126.880001))
		            //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer))
		            .icon(BitmapDescriptorFactory.fromBitmap(mBitmap))
		            .title("지명")
		            .snippet("구로디지탈 단지역"));
		
		//5. 마커를 누를때 나오는 infowindow 박스를 누를때 띄우는 alertDialog
		mGoogleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            
            @Override
            public void onInfoWindowClick(Marker marker) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapV2Activity.this);
                builder.setTitle("마커 정보")
                    .setMessage("해당 마커의 상세 정보 :" + marker.getSnippet())
                    .setPositiveButton("OK", new OnClickListener() {
                        
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                        }
                    })
                    .show();
            }
        });
    }
	
    private Bitmap overlay(Bitmap out, Bitmap in) {
        Bitmap bmOverlay = Bitmap.createBitmap(out.getWidth(), out.getHeight(), out.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(out, new Matrix(), null);
        
        Matrix matrix = new Matrix();
        matrix.postScale(0.7f, 0.7f, in.getWidth()/2, in.getHeight()/2);
        matrix.postTranslate((out.getWidth()-in.getWidth())/2, (out.getHeight()-in.getHeight())/2);
        
        canvas.drawBitmap(in, matrix, null);
        return bmOverlay;
    }
}
