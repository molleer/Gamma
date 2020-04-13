import React, { useMemo } from "react";
import {
    DigitText,
    DigitLayout,
    useDigitTranslations
} from "@cthit/react-digit-components";
import translations from "./MeGroups.screen.translations";
import DisplayGroupsTable from "../../../../common/elements/display-groups-table/DisplayGroupsTable.element";
import useGammaUser from "../../../../common/hooks/use-gamma-user/useGammaUser";

const MeGroups = () => {
    const [text] = useDigitTranslations(translations);
    const user = useGammaUser();

    const [activeGroups, pastGroups] = useMemo(
        () =>
            user == null
                ? [[], []]
                : [
                      user.relationships
                          .filter(g => g.group.active)
                          .map(g => g.group),
                      user.relationships
                          .filter(g => !g.group.active)
                          .map(g => g.group)
                  ],
        [user]
    );

    if (user == null) {
        return null;
    }

    if (activeGroups.length === 0 && pastGroups.length === 0) {
        return (
            <DigitLayout.Center>
                <DigitText.Heading3 text={text.NoGroupsForYou} />
            </DigitLayout.Center>
        );
    }

    return (
        <>
            {activeGroups.length > 0 && (
                <DisplayGroupsTable
                    title={text.ActiveGroups}
                    groups={activeGroups}
                />
            )}
            {pastGroups.length > 0 && (
                <DisplayGroupsTable
                    title={text.PastGroups}
                    groups={pastGroups}
                />
            )}
        </>
    );
};

export default MeGroups;
