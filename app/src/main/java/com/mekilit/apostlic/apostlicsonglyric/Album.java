package com.mekilit.apostlic.apostlicsonglyric;

/**
 * Created by Menasi on 6/18/2016.
 */
public class Album {


    private String Album_id;
    private String Album_Title;
    private String Album_Artist;
    private String Album_Art;
    private int _isSolo;



    public Album(String album_id, String album_Title, String album_Artist, int _isSolo) {
        Album_id = album_id;
        Album_Title = album_Title;
        Album_Artist = album_Artist;
        Album_Art= album_id;
        this._isSolo = _isSolo;
    }

    public String getAlbum_Art() {
        return Album_Art;
    }

    public String getAlbum_id() {
        return Album_id;
    }

    public String getAlbum_Title() {
        return Album_Title;
    }

    public String getAlbum_Artist() {
        return Album_Artist;
    }

    public int get_isSolo() {
        return _isSolo;
    }

    static Album[] insertAlbum =
            { new Album ("Aylew1","አምኜው ነው የተከተልኩት","አያሌው",1),
                    new Album ("Aylew2","እሱን ታምኖ","አያሌው",1),
                    new Album ("AberhamA1","ገና እንበዛለን ገና","አብርሃም አጌና",1),
                    new Album ("AberhamT1","አድርገህልኛል","አብርሃምና እያሱ ተ/ማርያም",1),
                    new Album ("AberhamT2","ዘመናት አይለውጡትም","አብርሃምና እያሱ ተ/ማርያም",1),
                    new Album ("Betty1","እኔን የሚገርመኝ ኢየሱስ","ቤቴልሄም አጥናፉ",1),
                    new Album ("Betty2","ኢየሱስ ይመጣል ","ቤቴልሄም አጥናፉ",1),
                    new Album ("Betty3","ክብሩን ይውሰድ","ቤቴልሄም አጥናፉ",1),
                    new Album ("Berhanu1","አምላኬ እግዚአብሔር አንድ ነው","ብርሀኑ ተክሌ",1),
                    new Album ("Berhanu2","አለን መልካም እረኛ","ብርሀኑ ተክሌ",1),
                    new Album ("Berhanu3","ክብረህ እንይህ ጌታ ሆይ","ብርሀኑ ተክሌ",1),
                    new Album ("Berhanu4","ባንተ ነው ኢየሱስ","ብርሀኑ ተክሌ",1),
                    new Album ("Carol1","የሕይወቴን ጅማሬ","ካሮል ፍቃደ",1),
                    new Album ("Carol2","ሕይወቱን ልኑሮው","ካሮል ፍቃደ",1),
                    new Album ("Elfu1","እግዚአብሔር መሃሪ ይቅር ባይ","እልፉ ኩርፉ",1),
                    new Album ("Elfu2","እናመሰግንሃለን","እልፉ ኩርፉ",1),
                    new Album ("Jerry1","ከምንም በላይ መዳኔ ይበልጣል","ኢየሩሳሌም ሞላ",1),
                    new Album ("Erkenesh1","ኢየሱስ ብቻ","እርቅነሽ ሰጋሮ",1),
                    new Album ("Fish1","ኑ ልንገራችሁ","ፍስሃ ይርጋ",1),
                    new Album ("Fish2","ጠላት እስከ መቼ","ፍስሃ ይርጋ",1),
                    new Album ("Fekade1","አምላክ አለኝ የማመልከው","ፍቃደ ደስታ",1),
                    new Album ("Gedion1","እጆቹ ለሰሩት","ጊዴዎን አዲስ",1),
                    new Album ("Gedion2","የጸሎት ቤት","ጊዴዎን አዲስ",1),
                    new Album ("Habtamu1","ፍጹም ከሰማይ ነው","ሀብታሙ ያለው",1),
                    new Album ("Hana1","አንተን ብዬ","ሐና መለሰ",1),
                    new Album ("Liya1","ካልባረከኝ አልለቅህም","ሊያና ነጻነት ታደሰ",1),
                    new Album ("Menber1","ጉልበት ሁሉ ይንበርከክለት","መንበረ ተሾመ",1),
                    new Album ("Menber2","የሚዱንትን በኛ ላይ ጨምር","መንበረ ተሾመ",1),
                    new Album ("MesertB1","ዛሬም አለኝ ምስጋና","መሰረት በላይ",1),
                    new Album ("MesertB2","ይውረድ መሰንቆዬ","መሰረት በላይ",1),
                    new Album ("MesertK1","አንድ ቀን","መሰረት ቀጀላ",1),
                    new Album ("Mulugeta1","በልዑል መጠጊያ","ሙሉጌታ ዮሃንስ",1),
                    new Album ("Mulugeta2","ቃልህን አምነዋለሁ","ሙሉጌታ ዮሃንስ",1),
                    new Album ("MuluKen1","ኢየሱስ ሰራቶኞችን ላክ","ሙሉቀን ተክሌ",1),
                    new Album ("Meselch","ሰው ቢችል","መሰለች አዘነ",1),
                    new Album ("Samson1","የድል ነሺ ድምጽ","ሳምሶም አደፍርስ",1),
                    new Album ("Sara1","በሌላ አይሁን","ሳራ ደምሰው",1),
                    new Album ("Sol1","ኃያል ነህ ብርቱ","ሰለሞን ተክሌ",1),
                    new Album ("Tadne1","ያያልበ ዙፋን ያለው","ታድን እንግዳ",1),
                    new Album ("Tewlde1","የድል ሽታ","ተወልደ አበባየሁ",1),
                    new Album ("Teru1","ቀን ለቀን ነገርን ታወጣለች","ጥሩነሽ በቀለ",1),
                    new Album ("Yemserach1","ለክብርህ ካልሆነ መኖሬ","የምስራች ገ/ሚካኤል",1),
                    new Album ("Yemserach2","ማደሪያዎችህ","የምስራች ገ/ሚካኤል",1),
                    new Album ("Zenbech1","ኢትዮጵያን እግዚአብሔር ይባርክ","ዘነበች መህዲ",1),
                    new Album ("hebret1","የሚመካ በእግዚአብሔር ይመካ","ሕብረት ቁጥር1",0),
                    new Album ("hebret2","ምስጋና  ሃሌሉያ","ሕብረት ቁጥር2",0),
                    new Album ("hebret3","የሠራዊት አምላክ ሆይ","ሕብረት ቁጥር3",0),
                    new Album ("hebret4","ውለታው በዝቶብኛል","ሕብረት ቁጥር4",0),
                    new Album ("hebret5","ማነህ አንተ ሰው የተሰቀልከው","ሕብረት ቁጥር5",0),
                    new Album ("hebret6","በሰማይ በዙፋን ላይ","ሕብረት ቁጥር6",0),
                    new Album ("hebret7","ሕይወት እውነት የሆነው ቃሌ","ሕብረት ቁጥር7",0),
                    new Album ("hebret8","የእውነት ወንጌል በዓለም","ሕብረት ቁጥር8",0),
                    new Album ("hebret11","ምስጋናው ይነሳ","ሕብረት ቁጥር11",0),
                    new Album ("hebret12","ዝራው ዘሩን በትነው","ሕብረት ቁጥር12",0),
                    new Album ("hebret13","ንገቲምዱ አጋና መዳናችን ድንቅ","ሕብረት ቁጥር13",0),
                    new Album ("hebret14","የቀናውን መንፈስ","ሕብረት ቁጥር14",0),
                    new Album ("hebret15","አምላኬ ክብር ዘምራለሁስ","ሕብረት ቁጥር15",0),
                    new Album ("hebret16","አያለሁ ገና አሜን","ሕብረት ቁጥር16",0),
                    new Album ("hebret17","እንሄዳለን እዚሁ ዘምረን","ሕብረት ቁጥር17",0),
                    new Album ("hebret18","ሰላም ለአንተ ይሁን","ሕብረት ቁጥር18",0),
                    new Album ("bebereya","ከጥንትም የነበረው","የኦክላንድ መዘምራን",0),
                    new Album ("wedding1","ጋብቻ ክቡር ቅዱስ"," የጋብቻ መዝሙር",0),
                    new Album ("kehulu1","ከሁሉ በላይ","የህብረት መዝሙር",0),
                    new Album ("hosana1","ሃሌሉያ","የሆሳእና ማራናታ መዘምራን",0),
                    new Album ("fishbermul","ባለብዙ ክብር","ፍሰሃ ሙሉጌታ እና ብርሃኑ",0),

            };

}
