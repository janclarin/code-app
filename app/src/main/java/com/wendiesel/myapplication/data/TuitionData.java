package com.wendiesel.myapplication.data;

import android.content.Context;

import com.wendiesel.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class TuitionData {
    private static final HashMap<String, String> salaryMapping = new HashMap<>();
    private static final HashMap<String, String> infoMapping = new HashMap<>();
    private static final String[][] subjectMapping = {
            // Math
            {"Architecture and related technologies", "Engineering", "Mathematics, computer and information sciences"},
            // Science
            {"Dentistry", "Engineering", "Medicine", "Nursing", "Pharmacy", "Physical and life sciences and technologies", "Veterinary medicine"},
            // English
            {"Education", "Humanities", "Visual and performing arts, and communications technologies", "Law, legal professions and studies", "Social and behavioural sciences"},
            // History
            {"Agriculture, natural resources and conservation", "Business management and public administration", "Humanities", "Law, legal professions and studies", "Social and behavioural sciences"},
            // Physical Education
            {"Medicine", "Nursing", "Other health, parks, recreation and fitness"}
    };

    static {
        salaryMapping.put("Agriculture, natural resources and conservation", "Natural and applied sciences and related occupations [C]");
        salaryMapping.put("Architecture and related technologies", "Natural and applied sciences and related occupations [C]");
        salaryMapping.put("Business management and public administration", "Professional occupations in business and finance [B0]");
        salaryMapping.put("Dentistry", "Professional occupations in health, nurse supervisors and registered nurses [D0-D1]");
        salaryMapping.put("Education", "Teachers and professors [E1 E130]  (9)");
        salaryMapping.put("Engineering", "Natural and applied sciences and related occupations [C]");
        salaryMapping.put("Humanities", "Occupations in social science, government service and religion [E0 E2]");
        salaryMapping.put("Law, legal professions and studies", "Occupations in social science, government service and religion [E0 E2]");
        salaryMapping.put("Mathematics, computer and information sciences", "Natural and applied sciences and related occupations [C]");
        salaryMapping.put("Medicine", "Professional occupations in health, nurse supervisors and registered nurses [D0-D1]");
        salaryMapping.put("Nursing", "Health occupations [D]");
        salaryMapping.put("Other health, parks, recreation and fitness", "Sales and service occupations not elsewhere classified, including occupations in travel and accommodation, attendants in recreation and sport as well as supervisors [G013-G016 G7 G9]");
        salaryMapping.put("Pharmacy", "Health occupations [D]");
        salaryMapping.put("Physical and life sciences and technologies", "Natural and applied sciences and related occupations [C]");
        salaryMapping.put("Social and behavioural sciences", "Occupations in social science, government service and religion [E0 E2]");
        salaryMapping.put("Veterinary medicine", "Technical, assisting and related occupations in health [D2-D3]");
        salaryMapping.put("Visual and performing arts, and communications technologies", "Occupations in art, culture, recreation and sport [F]");
        infoMapping.put("Agriculture, natural resources and conservation", "Agriculture is the cultivation of animals, plants, fungi, and other life forms for food, fiber, biofuel, medicinals and other products used to sustain and enhance human life. Natural resources occur naturally within environments that exist relatively undisturbed by humanity, in a natural form.");
        infoMapping.put("Architecture and related technologies", "Architecture is both the process and the product of planning, designing, and constructing buildings and other physical structures. Architectural works, in the material form of buildings, are often perceived as cultural symbols and as works of art.");
        infoMapping.put("Business management and public administration", "Management in business and organizations is the function that coordinates the efforts of people to accomplish goals and objectives using available resources efficiently and effectively.");
        infoMapping.put("Dentistry", "Dentistry is the branch of medicine that is involved in the study, diagnosis, prevention, and treatment of diseases, disorders and conditions of the oral cavity, commonly in the dentition but also the oral mucosa, and of adjacent and related structures and tissues, particularly in the maxillofacial (jaw and facial) area.");
        infoMapping.put("Education", "Education in its general sense is a form of learning in which the knowledge, skills, values, beliefs and habits of a group of people are transferred from one generation to the next through storytelling, discussion, teaching, training, and or research.");
        infoMapping.put("Engineering", "Engineering is the application of scientific, economic, social, and practical knowledge in order to invent, design, build, maintain, research, and improve structures, machines, devices, systems, materials and processes.");
        infoMapping.put("Humanities", "The humanities are academic disciplines that study human culture. The humanities use methods that are primarily critical, or speculative, and have a significant historical element—as distinguished from the mainly empirical approaches of the natural sciences. The humanities include literature, philosophy, religion, and musicology.");
        infoMapping.put("Law, legal professions and studies", "The practice of law involves giving legal advice to clients, drafting legal documents for clients, and representing clients in legal negotiations and court proceedings such as lawsuits, and is applied to the professional services of a lawyer or attorney at law, barrister, solicitor, or civil law notary.");
        infoMapping.put("Mathematics, computer and information sciences", "Mathematics is the study of topics such as quantity (numbers), structure, space, and change. Computer and information sciences are primarily concerned with the scientific and practical use of information and computing.");
        infoMapping.put("Medicine", "Medicine is the science or practice of the diagnosis, treatment, and prevention of disease. Medicine encompasses a variety of health care practices evolved to maintain and restore health by the prevention and treatment of illness.");
        infoMapping.put("Nursing", "Nursing is a profession within the health care sector focused on the care of individuals, families, and communities so they may attain, maintain, or recover optimal health and quality of life. Nurses may be differentiated from other health care providers by their approach to patient care, training, and scope of practice.");
        infoMapping.put("Other health, parks, recreation and fitness", "Recreation is an activity of leisure, leisure being discretionary time. Physical fitness is a general state of health and well-being and, more specifically, the ability to perform aspects of sports or occupations. A park is an area of open space provided for recreational use.");
        infoMapping.put("Pharmacy", "Pharmacy is the science and technique of preparing and dispensing drugs. It is a health profession that links health sciences with chemical sciences and aims to ensure the safe and effective use of pharmaceutical drugs.");
        infoMapping.put("Physical and life sciences and technologies", "The life sciences comprise the fields of science that involve the scientific study of living organisms – such as microorganisms, plants, animals, and human beings – as well as related considerations like bioethics. Physical science – branch of natural science that studies non-living systems, in contrast to life science.");
        infoMapping.put("Social and behavioural sciences", "Social sciences are concerned with society and the relationships among individuals within a society. The main social sciences include economics, human geography, political science, demography and sociology.");
        infoMapping.put("Veterinary medicine", "Veterinary medicine is the branch of medicine that deals with the prevention, diagnosis and treatment of disease, disorder and injury in animals. The scope of veterinary medicine is wide, covering all animal species, both domesticated and wild, with a wide range of conditions which can affect different species.");
        infoMapping.put("Visual and performing arts, and communications technologies", "The visual arts are art forms such as ceramics, drawing, painting, sculpture, printmaking, design, crafts, photography, video, filmmaking and architecture. Performing arts are art forms in which artists use their body, voice, or objects to convey artistic expression.");
    }

    private TreeSet<String> fieldOfInterests = new TreeSet<>();
    private HashMap<String, Integer> tuition = new HashMap<>();
    private JSONObject salary;

    /**
     * Creates new TuitionData object
     *
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
        salary = JsonDataReader.readObject(ctx, R.raw.salaryindustry2);
    }

    /**
     * Gets average tuition for a field of interest and province
     *
     * @param fieldOfInterest Field of interest
     * @param province        Province (or null for entire Canada)
     * @return Average tuition, in dollars
     */
    public int getAverageTuition(String fieldOfInterest, String province) {
        if (province == null) province = "Canada";
        Integer cost = tuition.get(fieldOfInterest + province);
        return (cost == null) ? 0 : cost;
    }

    /**
     * Gets a list of field of interests, sorted alphabetically
     *
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests() {
        return fieldOfInterests;
    }

    public enum SortOrder {
        ALPHABETICAL,
        TUITION_ASC,
        TUITION_DSC,
        SALARY_ASC,
        SALARY_DSC
    }

    /**
     * Gets a list of field of interests, with specific sort order
     * @param sortOrder Sort order
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests(SortOrder sortOrder) {
        Comparator<String> comp = null;
        switch (sortOrder) {
            case ALPHABETICAL:
                return getFieldOfInterests();
            case TUITION_ASC:
                comp = new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        Integer a = getAverageTuition(lhs, null), b = getAverageTuition(rhs, null);
                        return a.compareTo(b);
                    }
                };
                break;
            case TUITION_DSC:
                comp = new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        Integer a = getAverageTuition(lhs, null), b = getAverageTuition(rhs, null);
                        return -a.compareTo(b);
                    }
                };
                break;
            case SALARY_ASC:
                comp = new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        Double a = getAverageSalary(lhs, null), b = getAverageSalary(rhs, null);
                        return a.compareTo(b);
                    }
                };
                break;
            case SALARY_DSC:
                comp = new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {
                        Double a = getAverageSalary(lhs, null), b = getAverageSalary(rhs, null);
                        return -a.compareTo(b);
                    }
                };
                break;
        }
        ArrayList<String> foiList = new ArrayList<>(fieldOfInterests);
        Collections.sort(foiList, comp);
        return foiList;
    }

    /**
     * Gets a list of field of interests, sorted by ranking
     * Rankings should be relative
     *
     * @return List of field of interests
     */
    public Collection<String> getFieldOfInterests(int math, int science, int english, int history, int physed) {
        FoiRanking rankings[] = new FoiRanking[]{new FoiRanking(0, math), new FoiRanking(1, science), new FoiRanking(2, english), new FoiRanking(3, history), new FoiRanking(4, physed)};
        Arrays.sort(rankings);
        ArrayList<String> foiList = new ArrayList<>();
        HashSet<String> usedFoi = new HashSet<>();

        int curRank = -1;
        ArrayList<String> curList = new ArrayList<>();
        for (FoiRanking fr : rankings) {
            if (curRank != fr.rank) {
                curRank = fr.rank;
                Collections.sort(curList);
                foiList.addAll(curList);
                curList.clear();
            }
            for (String foi : subjectMapping[fr.idx]) {
                if (usedFoi.contains(foi)) continue;
                usedFoi.add(foi);
                curList.add(foi);
            }
        }
        Collections.sort(curList);
        foiList.addAll(curList);
        curList.clear();
        return foiList;
    }

    /**
     * Gets average salary (per hour) for a field of interest
     *
     * @param fieldOfInterest Field of interest
     * @param year        Year (1997-2015)
     * @return Average salary, per hour
     */
    public double getAverageSalary(String fieldOfInterest, int year) {
        String key = String.format("Jan-%d", year);
        try {
            return salary.getJSONObject(salaryMapping.get(fieldOfInterest)).getDouble(key);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Gets description for a field of interest
     *
     * @param fieldOfInterest Field of interest
     * @return Description of field of interest
     */
    public String getDescription(String fieldOfInterest) {
        String desc = infoMapping.get(fieldOfInterest);
        return desc != null ? desc : "";
    }

    /**
     * Gets salary category for a field of interest
     *
     * @param fieldOfInterest Field of interest
     * @return Salary category like "Health occupations"
     */
    public String getSalaryCategory(String fieldOfInterest) {
        String category = salaryMapping.get(fieldOfInterest);
        return category != null ? category : "";
    }

    private class FoiRanking implements Comparable {
        private int idx, rank;

        FoiRanking(int idx, int rank) {
            this.idx = idx;
            this.rank = rank;
        }

        @Override
        public int compareTo(Object another) {
            FoiRanking fr2 = (FoiRanking) another;
            if (rank < fr2.rank) return 1;
            else if (rank > fr2.rank) return -1;
            return 0;
        }
    }
}
