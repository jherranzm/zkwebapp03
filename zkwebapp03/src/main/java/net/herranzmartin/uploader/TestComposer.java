package net.herranzmartin.uploader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.herranzmartin.project977r.ExtractZipFile;
import net.herranzmartin.project977r.Procesa977R;

import org.zkoss.util.media.Media;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menuitem;

public class TestComposer extends SelectorComposer<Component>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 758991865359393313L;
	
	@Wire
	private Menuitem uploadFile;
	
	@Wire
	private Hbox resultado;
	
//    @Wire
//    private Footer footer_category;
//	 
//    private Grid gridFicheros;
// 
//	
//	public void doAfterCompose(Component comp) throws Exception {
//        super.doAfterCompose(comp);
//        gridFicheros = (Grid) comp;
//        ListaFicheros lf = new ListaFicheros();
//        gridFicheros.setModel(new ListModelList<Tbl000000>(lf.getAllFicheros()));
//        footer_category.setLabel("Hay " + lf.getAllFicheros().size() + " ficheros cargados.");
//    }	
	
	@Listen("onUpload = #uploadFile")
	public void gestionaFichero(UploadEvent event) throws InterruptedException{
		
		System.out.println("upLoadFile!!"+uploadFile.getLabel());
		Media media = event.getMedia();
		System.out.println("upLoadFile!!"+media.getContentType());
		System.out.println("upLoadFile!!"+media.getName());
		
		
	}
	
	@Listen("onUpload = #btnUpload")
	public void gestionaFicheros(UploadEvent event) throws InterruptedException{
		
		System.out.println("upLoadFile!!"+uploadFile.getLabel());
		
		ListaFicheros lf = new ListaFicheros();
		System.out.println("List.size():" + lf.getAllFicheros().size());
		
		Media[] medias = event.getMedias();
		if(medias == null){
			Messagebox.show("No se han subido ficheros!");
		}else{
			StringBuilder sb = new StringBuilder("Se han subido " + medias.length + " ficheros:" + "\n");
			List<String> listaFicheros = new ArrayList<String>();
			for(Media media : medias){
				sb.append("Tipo:"+media.getContentType() + "\n");
				sb.append("Nombre:"+media.getName() + "\n");
				
				String path = "/Users/jherranzm/dev/"+media.getName();
				sb.append("Nombre fichero:"+path + "\n");
				File file = new File(path);
				
				saveFile(media, file);
				listaFicheros.add(path);
			}
			ExtractZipFile ezf = new ExtractZipFile();
			for(String fName : listaFicheros){
				ezf.extraeFichero(fName);
			}
			
			for(String fName : ezf.getListaFicherosDescomprimidos()){
				Procesa977R proc = new Procesa977R(fName);
				proc.execute();
			}
			Messagebox.show(sb.toString());
		}
		
		lf = new ListaFicheros();
		String result = "ficheros.size():" + lf.getAllFicheros().size();
		
		resultado.appendChild(new Label(result));

		
		
	}

	/**
	 * @param media
	 * @param file
	 */
	private void saveFile(Media media, File file) {
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			InputStream fin = media.getStreamData();			
			in = new BufferedInputStream(fin);
			OutputStream fout = new FileOutputStream(file);
			out = new BufferedOutputStream(fout);
			byte buffer[] = new byte[1024];
			int ch = in.read(buffer);
			while (ch != -1) {
				out.write(buffer, 0, ch);
				ch = in.read(buffer);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if (out != null) 
					out.close();	
				
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

}
