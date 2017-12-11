package xyz.imaginatrix.synapse.util;

import io.realm.Realm;
import io.realm.RealmList;
import xyz.imaginatrix.synapse.data.models.Category;
import xyz.imaginatrix.synapse.data.models.Subject;

/**
 * Created by cyberpunk on 8/3/17.
 */

public class RealmUtil {

    // Yes, I hand-filled all this in, yes it was quite boring and time consumingly lazy haha.
    // Didn't feel like finding a way to automate it yet; perhaps in the future.
    public static boolean firstRunPopulateDatabase() {
        Realm realm = Realm.getDefaultInstance();
        // Method should only be called on first run and the entries should be the first things added to the database
        if (!realm.isEmpty()) {
            realm.close();
            return false;
        }

        try {
            realm.beginTransaction();
            // Create category

            final Category allTopics = realm.copyToRealm(new Category("*", "All Topics"));

            // Computer Science
            final Category ai = realm.copyToRealm(new Category("AI", "Artificial Intelligence"));
            final Category compLang = realm.copyToRealm(new Category("CL", "Computation and Language"));
            final Category compComplexity = realm.copyToRealm(new Category("CC", "Computational Complexity"));
            final Category compEngineering = realm.copyToRealm(new Category("CE", "Computational Engineering, Finance, and Science"));
            final Category compGeometry = realm.copyToRealm(new Category("CG", "Computational Geometry"));
            final Category csGameTheory = realm.copyToRealm(new Category("GT", "Computer Science and Game Theory"));
            final Category compVision = realm.copyToRealm(new Category("CV", "Computer Vision and Pattern Recognition"));
            final Category compSociety = realm.copyToRealm(new Category("CY", "Computers and Society"));
            final Category crypto = realm.copyToRealm(new Category("CR", "Cryptography and Security"));
            final Category dataStructures = realm.copyToRealm(new Category("DS", "Data Structures and Algorithms"));
            final Category databases = realm.copyToRealm(new Category("DB", "Databases"));
            final Category digitalLibs = realm.copyToRealm(new Category("DL", "Digital Libraries"));
            final Category discreteMath = realm.copyToRealm(new Category("DM", "Discrete Mathematics"));
            final Category distributedComputing = realm.copyToRealm(new Category("DC", "Distributed, Parallel, and Cluster Computing"));
            final Category emergingTech = realm.copyToRealm(new Category("ET", "Emerging Technologies"));
            final Category formalLanguages = realm.copyToRealm(new Category("FT", "Formal Languages and Automata Theory"));
            final Category genLit = realm.copyToRealm(new Category("GL", "General Literature"));
            final Category graphics = realm.copyToRealm(new Category("GR", "Graphics"));
            final Category hardwareArchitecture = realm.copyToRealm(new Category("AR", "Hardware Architecture"));
            final Category humanCompInteraction = realm.copyToRealm(new Category("HC", "Human-Computer Interaction"));
            final Category infoRetrieval = realm.copyToRealm(new Category("IR", "Information Retrieval"));
            final Category csInfoTheory = realm.copyToRealm(new Category("IT", "Information Theory"));
            final Category csMachineLearning = realm.copyToRealm(new Category("MLG", "Machine Learning"));
            final Category csLogic = realm.copyToRealm(new Category("LO", "Logic in Computer Science"));
            final Category mathSoft = realm.copyToRealm(new Category("MS", "Mathematical Software"));
            final Category multiAgentSys = realm.copyToRealm(new Category("MA", "Multi-agent Systems"));
            final Category multimedia = realm.copyToRealm(new Category("MM", "Multimedia"));
            final Category networking = realm.copyToRealm(new Category("NI", "Networking and Internet Architecture"));
            final Category neuralEvoComp = realm.copyToRealm(new Category("NN", "Neural and Evolutionary Computing"));
            final Category csNumerialAnalysis = realm.copyToRealm(new Category("NA", "Numerical Analysis"));
            final Category operatingSys = realm.copyToRealm(new Category("OS", "Operating Systems"));
            final Category csOther = realm.copyToRealm(new Category("OH", "Other"));
            final Category performance = realm.copyToRealm(new Category("PF", "Performance"));
            final Category programmingLanguages = realm.copyToRealm(new Category("PL", "Programming Languages"));
            final Category robotics = realm.copyToRealm(new Category("RO", "Robotics"));
            final Category socialNetworks = realm.copyToRealm(new Category("SI", "Social and Information Networks"));
            final Category softwareEngineering = realm.copyToRealm(new Category("SE", "Software Engineering"));
            final Category sound = realm.copyToRealm(new Category("SD", "Sound"));
            final Category symbolicComputation = realm.copyToRealm(new Category("SC", "Symbolic Computation"));
            final Category sysAndCtrl = realm.copyToRealm(new Category("SY", "Systems and Control"));

            RealmList<Category> compSciCategories = new RealmList<>(allTopics,
                    ai, compLang, compComplexity, compEngineering, compGeometry,
                    csGameTheory, compVision, compSociety, crypto, dataStructures,
                    databases, digitalLibs, discreteMath, distributedComputing, emergingTech,
                    formalLanguages, genLit, graphics, hardwareArchitecture, humanCompInteraction,
                    infoRetrieval, csInfoTheory, csMachineLearning, csLogic, mathSoft,
                    multiAgentSys, multimedia, networking, neuralEvoComp, csNumerialAnalysis,
                    operatingSys, csOther, performance, programmingLanguages, robotics,
                    socialNetworks, softwareEngineering, sound, symbolicComputation, sysAndCtrl);

            // Math
            final Category algGeometry = realm.copyToRealm(new Category("AG", "Algebraic Geometry"));
            final Category algTopology = realm.copyToRealm(new Category("AT", "Algebraic Topology"));
            final Category pdeAnalysis = realm.copyToRealm(new Category("AP", "Analysis of PDEs"));
            final Category catTheory = realm.copyToRealm(new Category("CT", "RawCategory Theory"));
            final Category classicalAnalysis = realm.copyToRealm(new Category("CA", "Classical Analysis and ODEs"));
            final Category combinatorics = realm.copyToRealm(new Category("CO", "Combinatorics"));
            final Category commAlg = realm.copyToRealm(new Category("AC", "Commutative Algebra"));
            final Category complexVars = realm.copyToRealm(new Category("CV", "Complex Variables"));
            final Category diffGeometry = realm.copyToRealm(new Category("DG", "Differential Geometry"));
            final Category dynamicSys = realm.copyToRealm(new Category("DS", "Dynamic Systems"));
            final Category funcAnalysis = realm.copyToRealm(new Category("FA", "Functional Analysis"));
            final Category genMath = realm.copyToRealm(new Category("GM", "General Mathematics"));
            final Category genTopology = realm.copyToRealm(new Category("GN", "General Topology"));
            final Category geoTopology = realm.copyToRealm(new Category("GT", "Geometric Topology"));
            final Category groupTheory = realm.copyToRealm(new Category("GR", "Group Theory"));
            final Category histOverview = realm.copyToRealm(new Category("HO", "History and Overview"));
            final Category mathInfoTheory = realm.copyToRealm(new Category("IT", "Information Theory"));
            final Category kTheory = realm.copyToRealm(new Category("KT", "K-Theory and Homology"));
            final Category mathLogic = realm.copyToRealm(new Category("LO", "Logic"));
            final Category mathPhysics = realm.copyToRealm(new Category("MP", "Mathematical Physics"));
            final Category metricGeometry = realm.copyToRealm(new Category("MG", "Metric Geometry"));
            final Category numberTheory = realm.copyToRealm(new Category("NT", "Number Theory"));
            final Category numericalAnalysis = realm.copyToRealm(new Category("NA", "Numerical Analysis"));
            final Category operatorAlg = realm.copyToRealm(new Category("OA", "Operator Algebras"));
            final Category optimization = realm.copyToRealm(new Category("OC", "Optimization and Control"));
            final Category probability = realm.copyToRealm(new Category("PR", "Probability"));
            final Category quantumAnalysis = realm.copyToRealm(new Category("QA", "Quantum Algebra"));
            final Category repTheory = realm.copyToRealm(new Category("RT", "Representation Theory"));
            final Category ringsAlgebras = realm.copyToRealm(new Category("RA", "Rings and Algebras"));
            final Category spectral = realm.copyToRealm(new Category("SP", "Spectral Theory"));
            final Category mathStatisticalTheory = realm.copyToRealm(new Category("ST", "Statistics Theory"));
            final Category sympleticGeometry = realm.copyToRealm(new Category("SG", "Symplectic Geometry"));

            RealmList<Category> mathCategories = new RealmList<>(allTopics,
                    algGeometry, algTopology, pdeAnalysis, catTheory, classicalAnalysis,
                    combinatorics, commAlg, complexVars, diffGeometry, dynamicSys,
                    funcAnalysis, genMath, genTopology, geoTopology, groupTheory,
                    histOverview, mathInfoTheory, kTheory, mathLogic, mathPhysics,
                    metricGeometry, numberTheory, numericalAnalysis, operatorAlg, optimization,
                    probability, quantumAnalysis, repTheory, ringsAlgebras, spectral,
                    mathStatisticalTheory, sympleticGeometry);

            // Statistics
            final Category applications = realm.copyToRealm(new Category("AP", "Applications"));
            final Category computation = realm.copyToRealm(new Category("CO", "Computation"));
            final Category statsMachineLearning = realm.copyToRealm(new Category("ML", "Machine Learning"));
            final Category methodology = realm.copyToRealm(new Category("ME", "Methodology"));
            final Category statsOther = realm.copyToRealm(new Category("OT", "Other"));
            final Category statsTheory = realm.copyToRealm(new Category("TH", "Theory"));

            RealmList<Category> statisticsCategories = new RealmList<>(allTopics,
                    applications, computation, statsMachineLearning, methodology, statsOther, statsTheory);

            // Quantitative Biology
            final Category biomolecules = realm.copyToRealm(new Category("BM", "Biomolecules"));
            final Category cellBehavior = realm.copyToRealm(new Category("CB", "Cell Behavior"));
            final Category genomics = realm.copyToRealm(new Category("GN", "Genomics"));
            final Category molecularNet = realm.copyToRealm(new Category("MN", "Molecular Networks"));
            final Category neuronsCognition = realm.copyToRealm(new Category("NC", "Neurons and Cognition"));
            final Category qbioOther = realm.copyToRealm(new Category("OT", "Other"));
            final Category popEvo = realm.copyToRealm(new Category("PE", "Populations and Evolution"));
            final Category quantMethods = realm.copyToRealm(new Category("QM", "Quantitative Methods"));
            final Category subcellularProcesses = realm.copyToRealm(new Category("SC", "Subcellular Processes"));
            final Category tissuesOrgans = realm.copyToRealm(new Category("TO", "Tissues and Organs"));

            RealmList<Category> qBioCategories = new RealmList<>(allTopics,
                    biomolecules, cellBehavior, genomics, molecularNet, neuronsCognition,
                    qbioOther, popEvo, quantMethods, subcellularProcesses, tissuesOrgans);

            // Quantitative Finance
            final Category compFinance = realm.copyToRealm(new Category("CP", "Computational Finance"));
            final Category economics = realm.copyToRealm(new Category("EC", "Economics"));
            final Category genFinance = realm.copyToRealm(new Category("GN", "General Finance"));
            final Category mathFinance = realm.copyToRealm(new Category("MF", "Mathematical Finance"));
            final Category portfolioMgmt = realm.copyToRealm(new Category("PM", "Portfolio Management"));
            final Category securities = realm.copyToRealm(new Category("PR", "Pricing of Securities"));
            final Category riskMgmt = realm.copyToRealm(new Category("RM", "Risk Management"));
            final Category statsFinance = realm.copyToRealm(new Category("ST", "Statistical Finance"));
            final Category trading = realm.copyToRealm(new Category("TR", "Trading and Market Microstructure"));

            RealmList<Category> qFinCategories = new RealmList<>(allTopics,
                    compFinance, economics, genFinance, mathFinance, portfolioMgmt,
                    securities, riskMgmt, statsFinance, trading);

            // Regular Physics
            final Category accelPhysics = realm.copyToRealm(new Category("acc-ph", "Accelerator Physics"));
            final Category atmosPhysics = realm.copyToRealm(new Category("ao-ph", "Atmospheric and Oceanic Physics"));
            final Category atomicPhysics = realm.copyToRealm(new Category("atom-ph", "Atomic Physics"));
            final Category molecularClusters = realm.copyToRealm(new Category("atm-clus", "Atomic and Molecular Clusters"));
            final Category bioPhysics = realm.copyToRealm(new Category("bio-ph", "Biological Physics"));
            final Category chemPhysics = realm.copyToRealm(new Category("chem-ph", "Chemical Physics"));
            final Category classicalPhysics = realm.copyToRealm(new Category("class-ph", "Classical Physics"));
            final Category compPhysics = realm.copyToRealm(new Category("comp-ph", "Computational Physics"));
            final Category dataAnalytics = realm.copyToRealm(new Category("data-an", "Data Analysis, Statistics, and Probability"));
            final Category fluidDynamics = realm.copyToRealm(new Category("flu-dyn", "Fluid Dynamics"));
            final Category generalPhysics = realm.copyToRealm(new Category("gen-ph", "General Physics"));
            final Category geophysics = realm.copyToRealm(new Category("geo-ph", "Geophysics"));
            final Category physicsHistory = realm.copyToRealm(new Category("hist-ph", "History of Physics"));
            final Category instrumentation = realm.copyToRealm(new Category("ins-det", "Instrumentation and Detectors"));
            final Category medPhysics = realm.copyToRealm(new Category("med-ph", "Medical Physics"));
            final Category optics = realm.copyToRealm(new Category("optics", "Optics"));
            final Category physicsEdu = realm.copyToRealm(new Category("ed-ph", "Physics Education"));
            final Category physicsAndSociety = realm.copyToRealm(new Category("soc-ph", "Physics and Society"));
            final Category plasmaPhysics = realm.copyToRealm(new Category("plasm-ph", "Plasma Physics"));
            final Category popularPhysics = realm.copyToRealm(new Category("pop-ph", "Popular Physics"));
            final Category spacePhysics = realm.copyToRealm(new Category("space-ph", "Space Physics"));

            RealmList<Category> physicsCategories = new RealmList<>(allTopics,
                    accelPhysics, atmosPhysics, atomicPhysics, molecularClusters, bioPhysics,
                    chemPhysics, classicalPhysics, compPhysics, dataAnalytics, fluidDynamics,
                    generalPhysics, geophysics, physicsHistory, instrumentation, medPhysics,
                    optics, physicsEdu, physicsAndSociety, plasmaPhysics, popularPhysics, spacePhysics);

            // Astrophysics
            final Category astroOfGalaxies = realm.copyToRealm(new Category("GA", "Astrophysics of Galaxies"));
            final Category cosmology = realm.copyToRealm(new Category("CO", "Cosmology and Nongalactic Astrophysics"));
            final Category planetaryAstrophysics = realm.copyToRealm(new Category("EP", "Earth and Planetary Astrophysics"));
            final Category highEnergyPhenomenon = realm.copyToRealm(new Category("HE", "High Energy Astrophysical Phenomena"));
            final Category astroInstrumentation = realm.copyToRealm(new Category("IM", "Instrumentation and Methods for Astrophysics"));
            final Category solarAstrophysics = realm.copyToRealm(new Category("SR", "Solar and Stellar Astrophysics"));

            RealmList<Category> astrophysicsCategories = new RealmList<>(allTopics,
                    astroOfGalaxies, cosmology, planetaryAstrophysics, highEnergyPhenomenon, astroInstrumentation, solarAstrophysics);

            // Nonlinear Sciences
            final Category adaptation = realm.copyToRealm(new Category("AO", "Adaptation and Self-Organizing Systems"));
            final Category cellularAutomata = realm.copyToRealm(new Category("CG", "Cellular Automata and Lattice Gases"));
            final Category chaoticDynamics = realm.copyToRealm(new Category("CD", "Chaotic Dynamics"));
            final Category solvableSystems = realm.copyToRealm(new Category("SI", "Exactly Solvable and Integrable Systems"));
            final Category solitons = realm.copyToRealm(new Category("PS", "Pattern Formation and Solitons"));

            RealmList<Category> nonlinearCategories = new RealmList<>(allTopics,
                    adaptation, cellularAutomata, chaoticDynamics, solvableSystems, solitons);

            // Condensed Matter
            final Category disorderedSystems = realm.copyToRealm(new Category("dis-nn", "Disordered Systems and Neural Networks"));
            final Category materialsSci = realm.copyToRealm(new Category("mtrl-sci", "Materials Science"));
            final Category mesoNanoPhysics = realm.copyToRealm(new Category("mes-hall", "Mesoscale and Nanoscale Physics"));
            final Category condMatOther = realm.copyToRealm(new Category("other", "Other"));
            final Category quantumGases = realm.copyToRealm(new Category("quant-gas", "Quantum Gases"));
            final Category softMatter = realm.copyToRealm(new Category("soft", "Soft Condensed Matter"));
            final Category statMechanics = realm.copyToRealm(new Category("stat-met", "Statistical Mechanics"));
            final Category correlatedElectrons = realm.copyToRealm(new Category("str-el", "Strongly Correlated Electrons"));
            final Category superconductivity = realm.copyToRealm(new Category("supr-con", "Superconductivity"));

            RealmList<Category> condMatCategories = new RealmList<>(allTopics,
                    disorderedSystems, materialsSci, mesoNanoPhysics, condMatOther, quantumGases,
                    softMatter, statMechanics, correlatedElectrons, superconductivity);


            realm.copyToRealm(new Subject("cs", "Computer Science", compSciCategories));
            realm.copyToRealm(new Subject("math", "Mathematics", mathCategories));
            realm.copyToRealm(new Subject("stat", "Statistics", statisticsCategories));
            realm.copyToRealm(new Subject("q-bio", "Quantitative Biology", qBioCategories));
            realm.copyToRealm(new Subject("q-fin", "Quantitative Finance", qFinCategories));
            realm.copyToRealm(new Subject("nlin", "Nonlinear Sciences", nonlinearCategories));
            realm.copyToRealm(new Subject("physics", "Physics", physicsCategories));
            realm.copyToRealm(new Subject("astro-ph", "Physics: Astrophysics", astrophysicsCategories));
            realm.copyToRealm(new Subject("cond-mat", "Physics: Condensed Matter", condMatCategories));
            realm.copyToRealm(new Subject("gr-qc", "Physics: General Relativity and Quantum Cosmology", null));
            realm.copyToRealm(new Subject("hep-ex", "Physics: High Energy (Experimental)", null));
            realm.copyToRealm(new Subject("hep-lat", "Physics: High Energy (Lattice)", null));
            realm.copyToRealm(new Subject("hep-ph", "Physics: High Energy (Phenomenology)", null));
            realm.copyToRealm(new Subject("hep-th", "Physics: High Energy (Theory)", null));
            realm.copyToRealm(new Subject("math-ph", "Physics: Mathematical", null));
            realm.copyToRealm(new Subject("nucl-ex", "Physics: Nuclear Experimentation", null));
            realm.copyToRealm(new Subject("nucl-th", "Physics: Nuclear Theory", null));
            realm.copyToRealm(new Subject("quant-ph", "Quantum Physics", null));

            realm.commitTransaction();
            realm.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
