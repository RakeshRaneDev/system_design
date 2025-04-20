package org.binary_brush_stroke.structural.facade.computer;


class Computer {
    private Memory memory;
    private HardDrive hardDrive;
    private CPU cpu;

    Computer() {
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
        this.cpu = new CPU();
    }

    public void processMyData() {
        memory.loadMemory();
        hardDrive.loadData();
        cpu.process();
        System.out.println("Data successfully process");
    }
}
