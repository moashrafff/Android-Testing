package com.moashraf.testingcourse

class LeadsController {

    val leadsList = mutableListOf<Lead>()

    fun addLeads(lead: Lead) {
        if (isValidPhoneNumber(lead.phoneNumber) && isUniqueLead(lead)) {
        leadsList.add(lead)
        }
    }

    fun getLeads() : List<Lead> = leadsList

    private fun isValidFirstName(firstName: String) : Boolean = firstName.length >= 3

    private fun isValidPhoneNumber (phoneNumber : String) : Boolean = phoneNumber.length == 11 && phoneNumber.all { char -> char.isDigit() }

    private fun isUniqueLead (lead: Lead) : Boolean = leadsList.none { it.firstName == lead.firstName && it.lastName == lead.lastName && it.phoneNumber == lead.phoneNumber }
}