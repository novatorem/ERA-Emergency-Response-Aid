package com.csc301.team7.era;

public class DiagnosedIssue extends HealthItem {

    /// <summary>
    /// ICD code
    /// </summary>
    public String Icd;

    /// <summary>
    /// ICD name
    /// </summary>
    public String IcdName;

    /// <summary>
    /// Profesional name
    /// </summary>
    public String ProfName;

    /// <summary>
    /// Probability for the issue in %
    /// </summary>
    public float Accuracy;

    public int Ranking;
}
