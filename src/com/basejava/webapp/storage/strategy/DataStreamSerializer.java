package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements IOStrategy {

    @Override
    public void doWrite(Resume r, OutputStream stream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(stream)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeList(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            writeList(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                dos.writeUTF(type.name());
                AbstractSection section = entry.getValue();
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeList(dos, ((ListSection) section).getList(), dos::writeUTF);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeList(dos, ((OrganizationSection) section).getOrganizations(), entry1 -> {
                            dos.writeUTF(entry1.getTitle());
                            dos.writeUTF(entry1.getWebsite().getName());
                            dos.writeUTF(entry1.getWebsite().getUrl());
                            writeList(dos, entry1.getPeriods(), entry2 -> {
                                writeLocalDate(dos, entry2.getFrom());
                                writeLocalDate(dos, entry2.getTo());
                                dos.writeUTF(entry2.getPosition());
                                dos.writeUTF(entry2.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream stream) {
        try (DataInputStream dis = new DataInputStream(stream)) {
            Resume r = new Resume(dis.readUTF(), dis.readUTF());
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                r.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(sectionType, new ListSection((ArrayList<String>) (readList(dis, dis::readUTF))));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        r.addSection(sectionType, new OrganizationSection((ArrayList<Organization>)
                                readList(dis, () -> {
                                    Organization org = new Organization(dis.readUTF(), dis.readUTF(), dis.readUTF());
                                    ArrayList<Period> periods = (ArrayList<Period>)
                                        readList(dis, () ->
                                            new Period(
                                                readLocalDate(dis),
                                                readLocalDate(dis),
                                                dis.readUTF(),
                                                dis.readUTF()
                                            )
                                        );
                                    periods.forEach(org::addPeriod);
                                    return org;
                                })
                        ));
                        break;
                }
            }
            return r;
        } catch (IOException e) {
            throw new StorageException("Error read resume", e);
        }
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonth().getValue());
    }

    private <T> void writeList(DataOutputStream dos, Collection<T> list, writeFunction<T> function) throws IOException {
        dos.writeInt(list.size());
        for (T item : list) {
            function.write(item);
        }
    }

    private interface writeFunction<LT> {
        void write(LT entry) throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, readFunction<T> function) throws IOException {
        int listSize = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(function.read());
        }
        return list;
    }

    private interface readFunction<T> {
        T read() throws IOException;
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }
}