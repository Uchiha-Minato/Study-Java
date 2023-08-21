package com.lhy.dsp;

import com.lhy.dsp.component.MilitaryPerson;
import com.lhy.dsp.compositenode.MilitaryOfficer;
import com.lhy.dsp.leafnode.MilitarySoldier;

import java.util.Iterator;

public class Application_Composite {
    public static void main(String[] args) {
        MilitaryPerson companyCommander = new MilitaryOfficer("连长", 5000);
        MilitaryPerson platoonLeader1 = new MilitaryOfficer("一排长", 4000);
        MilitaryPerson platoonLeader2 = new MilitaryOfficer("二排长", 4000);
        companyCommander.add(platoonLeader1);
        companyCommander.add(platoonLeader2);
        MilitaryPerson squadLeader11 = new MilitaryOfficer("班长11", 2000);
        MilitaryPerson squadLeader12 = new MilitaryOfficer("班长12", 2000);
        MilitaryPerson squadLeader13 = new MilitaryOfficer("班长13", 2000);
        MilitaryPerson squadLeader21 = new MilitaryOfficer("班长21", 2000);
        MilitaryPerson squadLeader22 = new MilitaryOfficer("班长22", 2000);
        MilitaryPerson squadLeader23 = new MilitaryOfficer("班长23", 2000);
        platoonLeader1.add(squadLeader11);
        platoonLeader1.add(squadLeader12);
        platoonLeader1.add(squadLeader13);
        platoonLeader2.add(squadLeader21);
        platoonLeader2.add(squadLeader22);
        platoonLeader2.add(squadLeader23);
        MilitaryPerson[] soldiers = new MilitarySoldier[90];
        for (int i = 0; i < soldiers.length; i++) {
            soldiers[i] = new MilitarySoldier("士兵" + i, 1000);
        }
        for (int i = 0; i < 15; i++) {
            squadLeader11.add(soldiers[i]);
            squadLeader12.add(soldiers[i + 15]);
            squadLeader13.add(soldiers[i + 30]);
            squadLeader21.add(soldiers[i + 45]);
            squadLeader22.add(soldiers[i + 60]);
            squadLeader23.add(soldiers[i + 75]);
        }

        double salary = computeSalary(platoonLeader1);
        System.out.println("一排的军饷：" + salary);
        salary = computeSalary(squadLeader21);
        System.out.println("二排一班的军饷：" + salary);
        salary = computeSalary(companyCommander);
        System.out.println("全连的军饷：" + salary);
    }

    public static double computeSalary(MilitaryPerson person) {
        double sum = 0;
        sum = sum + person.getSalary();
        Iterator<MilitaryPerson> iterator = person.getAllChildren();
        ;
        if (iterator != null) {
            while (iterator.hasNext()) {
                MilitaryPerson p = iterator.next();
                sum += computeSalary(p);
            }
        }
        return sum;
    }
}
