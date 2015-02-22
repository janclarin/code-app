package com.wendiesel.myapplication.data;

import android.content.Context;

import com.wendiesel.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class TuitionData {
    private static final HashMap<String,String> salaryMapping = new HashMap<>();
    private static final HashMap<String,String> infoMapping = new HashMap<>();
    private static final String[][] subjectMapping = {
            // Math
            {"Architecture and related technologies","Engineering","Mathematics, computer and information sciences"},
            // Science
            {"Dentistry","Engineering","Medicine","Nursing","Pharmacy","Physical and life sciences and technologies","Veterinary medicine"},
            // English
            {"Education","Humanities","Visual and performing arts, and communications technologies","Law, legal professions and studies","Social and behavioural sciences"},
            // History
            {"Agriculture, natural resources and conservation","Business management and public administration","Humanities","Law, legal professions and studies","Social and behavioural sciences"},
            // Physical Education
            {"Medicine","Nursing","Other health, parks, recreation and fitness"}
    };
    static {
        salaryMapping.put("Agriculture, natural resources and conservation","Occupations unique to processing, manufacturing and utilities");
        salaryMapping.put("Architecture and related technologies","Occupations in art, culture, recreation and sport");
        salaryMapping.put("Business management and public administration","Management occupations");
        salaryMapping.put("Dentistry","Health occupations");
        salaryMapping.put("Education","Occupations in social science, education, government service and religion");
        salaryMapping.put("Engineering","Natural and applied sciences and related occupations");
        salaryMapping.put("Humanities","Occupations in social science, education, government service and religion");
        salaryMapping.put("Law, legal professions and studies","Business, finance and administrative occupations");
        salaryMapping.put("Mathematics, computer and information sciences","Natural and applied sciences and related occupations");
        salaryMapping.put("Medicine","Health occupations");
        salaryMapping.put("Nursing","Health occupations");
        salaryMapping.put("Other health, parks, recreation and fitness","Occupations in art, culture, recreation and sport");
        salaryMapping.put("Pharmacy","Health occupations");
        salaryMapping.put("Physical and life sciences and technologies","Natural and applied sciences and related occupations");
        salaryMapping.put("Social and behavioural sciences","Occupations in social science, education, government service and religion");
        salaryMapping.put("Veterinary medicine","Health occupations");
        salaryMapping.put("Visual and performing arts, and communications technologies","Occupations in art, culture, recreation and sport");
        infoMapping.put("Agriculture, natural resources and conservation","Agriculture is the cultivation of animals, plants, fungi, and other life forms for food, fiber, biofuel, medicinals and other products used to sustain and enhance human life. Natural resources occur naturally within environments that exist relatively undisturbed by humanity, in a natural form.");
        infoMapping.put("Architecture and related technologies","Architecture is both the process and the product of planning, designing, and constructing buildings and other physical structures. Architectural works, in the material form of buildings, are often perceived as cultural symbols and as works of art.");
        infoMapping.put("Business management and public administration","Management in business and organizations is the function that coordinates the efforts of people to accomplish goals and objectives using available resources efficiently and effectively. Management includes planning, organizing, staffing, leading or directing, and controlling an organization to accomplish the goal.");
        infoMapping.put("Dentistry","Dentistry is the branch of medicine that is involved in the study, diagnosis, prevention, and treatment of diseases, disorders and conditions of the oral cavity, commonly in the dentition but also the oral mucosa, and of adjacent and related structures and tissues, particularly in the maxillofacial (jaw and facial) area.");
        infoMapping.put("Education","Education in its general sense is a form of learning in which the knowledge, skills, values, beliefs and habits of a group of people are transferred from one generation to the next through storytelling, discussion, teaching, training, and or research. Education is commonly and formally divided into stages such as preschool, primary school, secondary school and then college, university or apprenticeship.");
        infoMapping.put("Engineering","Engineering is the application of scientific, economic, social, and practical knowledge in order to invent, design, build, maintain, research, and improve structures, machines, devices, systems, materials and processes.  Engineering is a broad discipline which is often broken down into several sub-disciplines, including chemical engineering, civil engineering, electrical engineering, and mechanical engineering.");
        infoMapping.put("Humanities","The humanities are academic disciplines that study human culture. The humanities use methods that are primarily critical, or speculative, and have a significant historical element—as distinguished from the mainly empirical approaches of the natural sciences. The humanities include ancient and modern languages, literature, philosophy, religion, and musicology.");
        infoMapping.put("Law, legal professions and studies","The practice of law involves giving legal advice to clients, drafting legal documents for clients, and representing clients in legal negotiations and court proceedings such as lawsuits, and is applied to the professional services of a lawyer or attorney at law, barrister, solicitor, or civil law notary.");
        infoMapping.put("Mathematics, computer and information sciences","Mathematics is the study of topics such as quantity (numbers), structure, space, and change. Computer and information sciences are primarily concerned with the scientific and practical use of information and computing.");
        infoMapping.put("Medicine","Medicine is the science or practice of the diagnosis, treatment, and prevention of disease. Medicine encompasses a variety of health care practices evolved to maintain and restore health by the prevention and treatment of illness. In modern clinical practice, doctors personally assess patients in order to diagnose, treat, and prevent disease using clinical judgment.");
        infoMapping.put("Nursing","Nursing is a profession within the health care sector focused on the care of individuals, families, and communities so they may attain, maintain, or recover optimal health and quality of life. Nurses may be differentiated from other health care providers by their approach to patient care, training, and scope of practice.");
        infoMapping.put("Other health, parks, recreation and fitness","Recreation is an activity of leisure, leisure being discretionary time. Physical fitness is a general state of health and well-being and, more specifically, the ability to perform aspects of sports or occupations. A park is an area of open space provided for recreational use.");
        infoMapping.put("Pharmacy","Pharmacy is the science and technique of preparing and dispensing drugs. It is a health profession that links health sciences with chemical sciences and aims to ensure the safe and effective use of pharmaceutical drugs. An establishment in which pharmacy (in the first sense) is practiced is called a pharmacy.");
        infoMapping.put("Physical and life sciences and technologies","The life sciences comprise the fields of science that involve the scientific study of living organisms – such as microorganisms, plants, animals, and human beings – as well as related considerations like bioethics. Physical science – branch of natural science that studies non-living systems, in contrast to life science.");
        infoMapping.put("Social and behavioural sciences","Social sciences are concerned with society and the relationships among individuals within a society. The main social sciences include economics, human geography, political science, demography and sociology. Behavioral science is the systematic analysis and investigation of human and animal behaviour through controlled and naturalistic observation, and disciplined scientific experimentation.");
        infoMapping.put("Veterinary medicine","Veterinary medicine is the branch of medicine that deals with the prevention, diagnosis and treatment of disease, disorder and injury in animals. The scope of veterinary medicine is wide, covering all animal species, both domesticated and wild, with a wide range of conditions which can affect different species.");
        infoMapping.put("Visual and performing arts, and communications technologies","The visual arts are art forms such as ceramics, drawing, painting, sculpture, printmaking, design, crafts, photography, video, filmmaking and architecture. Performing arts are art forms in which artists use their body, voice, or objects to convey artistic expression—as opposed to, for example, purely visual arts, in which artists use paint/canvas or various materials to create physical or static art objects.");
    }
    private TreeSet<String> fieldOfInterests = new TreeSet<>();
    private HashMap<String, Integer> tuition = new HashMap<>();
    private JSONObject salary;

    /**
     * Creates new TuitionData object
     * @param ctx Context
     */
    public TuitionData(Context ctx) {
        JSONArray data = JsonDataReader.readArray(ctx, R.raw.averagetuition);
        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject e = data.getJSONObject(i);
                if (!e.getString("Ref_Date").equals("2014/2015")) continue;
                fieldOfInterests.add(e.getString("GROUP"));
                tuition.put(e.getString("GROUP") + e.getString("GEO"), e.getInt("Value"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        salary = JsonDataReader.readObject(ctx, R.raw.salaryindustry);
    }

    /**
     * Gets average tuition for a field of interest and province
     * @param fieldOfInterest Field of interest
     * @param province Province (or null for entire Canada)
     * @return Average tuition, in dollars
     */
    public int getAverageTuition(String fieldOfInterest, String province) {
        if (province == null) province = "Canada";
        Integer cost = tuition.get(fieldOfInterest + province);
        return (cost == null) ? 0 : cost;
    }

    /**
     * Gets a list of field of interests, sorted alphabetically
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests() {
        return fieldOfInterests;
    }

    private class FoiRanking implements Comparable {
        private int idx, rank;

        @Override
        public int compareTo(Object another) {
            FoiRanking fr2 = (FoiRanking) another;
            if (rank < fr2.rank) return 1;
            else if (rank > fr2.rank) return -1;
            return 0;
        }

        FoiRanking(int idx, int rank) {
            this.idx = idx;
            this.rank = rank;
        }
    }

    /**
     * Gets a list of field of interests, sorted by ranking
     * Rankings should be relative
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests(int math, int science, int english, int history, int physed) {
        FoiRanking rankings[] = new FoiRanking[]{new FoiRanking(0,math), new FoiRanking(1,science), new FoiRanking(2,english), new FoiRanking(3,history), new FoiRanking(4,physed)};
        Arrays.sort(rankings);
        ArrayList<String> foiList = new ArrayList<>();
        HashSet<String> usedFoi = new HashSet<>();
        for (FoiRanking fr : rankings) {
            for (String foi : subjectMapping[fr.idx]) {
                if (usedFoi.contains(foi)) continue;
                usedFoi.add(foi);
                foiList.add(foi);
            }
        }
        return foiList;
    }

    /**
     * Gets average salary (per hour) for a field of interest
     * @param fieldOfInterest Field of interest
     * @param province Province (or null for entire Canada)
     * @return Average salary, per hour
     */
    public double getAverageSalary(String fieldOfInterest, String province) {
        if (province == null) province = "Canada";
        try {
            return salary.getJSONObject(province).getDouble(salaryMapping.get(fieldOfInterest));
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Gets description for a field of interest
     * @param fieldOfInterest Field of interest
     * @return Description of field of interest
     */
    public String getDescription(String fieldOfInterest) {
        String desc = infoMapping.get(fieldOfInterest);
        return desc != null ? desc : "";
    }

    public String getSalaryCategory(String fieldOfInterest) {
        String category = salaryMapping.get(fieldOfInterest);
        return category != null ? category : "";
    }
}
