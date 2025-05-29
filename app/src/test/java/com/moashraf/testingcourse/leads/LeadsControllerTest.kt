package com.moashraf.testingcourse.leads

import com.moashraf.testingcourse.Lead
import com.moashraf.testingcourse.LeadsController
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class LeadsControllerTest {

    @Test
    fun `Given valid lead when addLeads then return one item list` (){
        val lead = Lead(firstName = "Nadine", lastName = "Islam", phoneNumber =  "01026656520")
        val controller = LeadsController()
        controller.addLeads(lead)
        val leads = controller.getLeads()
        assertEquals(1, leads.size)
    }

    @Test
    fun `Given lead with two characters in first name when calling add lead then return leads is empty` () {
        val lead = Lead(firstName = "mo", lastName = "ashraf", phoneNumber = "1234")
        val controller = LeadsController()
        controller.addLeads(lead)
        val leads = controller.getLeads()
        assertEquals(0, leads.size)
    }

    @Test
    fun `Given lead with two characters in last name when calling add lead then return leads is empty` () {
        val lead = Lead(firstName = "Mohamed", lastName = "as", phoneNumber = "1234")
        val controller = LeadsController()
        controller.addLeads(lead)
        val leads = controller.getLeads()
        assertEquals(0, leads.size)
    }

    @Test
    fun `Given lead with invalid phone number when calling add lead then return leads is empty` () {
        val lead = Lead(firstName = "Mohamed", lastName = "ashraf", phoneNumber = "1234")
        val controller = LeadsController()
        controller.addLeads(lead)
        assertTrue(controller.getLeads().isEmpty())
    }

    @Test
    fun `Given lead with invalid phone number with chars not digits when calling add lead then return leads is empty` () {
        val lead = Lead(firstName = "Mohamed", lastName = "ashraf", phoneNumber = "aaaaaaaaaaa")
        val controller = LeadsController()
        controller.addLeads(lead)
        assertTrue(controller.getLeads().isEmpty())
    }

    @Test
    fun `Given lead with duplicated info when calling add lead then return leads with one item` () {
        val lead = Lead(firstName = "Mohamed", lastName = "ashraf", phoneNumber = "01026656520")
        val lead2 = Lead(firstName = "Mohamed", lastName = "ashraf", phoneNumber = "01026656520")
        val controller = LeadsController()
        controller.addLeads(lead)
        controller.addLeads(lead2)
        assertEquals(1, controller.getLeads().size)
    }
}